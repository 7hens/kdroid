package cn.thens.kdroid.web

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import java.util.regex.Pattern

/**
 * jBridge 是一个十分轻量且安全的 Javascript Bridge，并在原生和 Web 端提供了一致的API和开发体验。
 *
 * native 端：
 * -  创建 jBridge => `val jBridge = JBridge.create(webView)`
 * -  注册原生方法 => `jBridge.register("nativeMethod") { arg, callback -> callback("jsCallback") }`
 * -  调用 JS 方法 => `jBridge.invoke("jsMethod", "jsArg") { arg -> }`
 *
 * web 端：在使用之前建议手动添加代码：`<script src="https://7hens.cn/kdroid/jbridge.min.js"></script>`
 * -  注册 JS 方法 => `jBridge.register("jsMethod", function (arg, callback) { callback("nativeCallback") })`
 * -  调用原生方法 => `jBridge.invoke("nativeMethod", "nativeArg", function (arg) { })`
 *
 * **注意：**
 * jBridge 在创建的时候给 WebView 绑定了一个 WebChromeClient。
 * 若用户需要自定义 WebChromeClient，请参照 [JBridge.chromeClient]
 */
open class JBridge private constructor(private val js: (String) -> Unit) : Bridge {
    private val bridge = BridgeImpl(object : BridgeImpl.Interface {
        override fun func(name: String, arg: String) {
            js("jBridge.__func('${name.quoted()}', '${arg.quoted()}');")
        }

        override fun callback(name: String, arg: String) {
            js("jBridge.__callback('${name.quoted()}', '${arg.quoted()}');")
        }

        private fun String.quoted(): String = replace("'", "\\'")
    })

    /**
     * 注册一个原生方法，供 JS 调用
     * 重复调用会发生覆盖。
     * @param func 方法名
     * @param handler 方法体
     */
    override fun register(func: String, handler: Bridge.Handler) {
        bridge.register(func, handler)
    }

    /**
     * 注销原生接口
     * @param func 方法名
     */
    override fun unregister(func: String) {
        bridge.unregister(func)
    }

    /**
     * 调用 JS 的方法
     * @param func 方法名
     * @param arg 参数
     * @param callback 回调，可多次调用
     */
    override fun invoke(func: String, arg: String, callback: Bridge.Callback) {
        bridge.invoke(func, arg, callback)
    }

    /**
     * 如果 WebView 需要使用自定义的 WebChromeClient，则需要重写并调用 jBridge 的 onJsPrompt 和 onProgressChanged：
     *
     * ```kotlin
     * val jBridge = JBridge.create(webView)
     *
     * webView.webChromeClient = object: WebChromeClient() {
     *      override fun onProgressChanged(view: WebView?, newProgress: Int) {
     *          jBridge.onProgressChanged(view, newProgress)
     *          //...其他逻辑写在这里
     *      }
     *
     *      override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
     *          if (jBridge.chromeClient(view, url, message, defaultValue, result)) return true
     *          //...其他逻辑写在这里
     *      }
     * }
     * ```
     */
    val chromeClient: WebChromeClient by lazy {
        val nativeTrigger = NativeTrigger(bridge.nativeInterface)
        object : WebChromeClient() {
            @Suppress("DeprecatedCallableAddReplaceWith")
            @Deprecated("JS 的注入时机不可控，建议手动注入")
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress >= 10) js(nativeTrigger.getInitJsCode("prompt"))
            }

            /**
             * 在 web 端需要通过 prompt() 来调用 native 端的接口。
             * 如果需要使用自定义的 WebChromeClient 需要重写此方法，并调用 jBridge.onJsPrompt
             */
            override fun onJsPrompt(view: WebView?, url: String?, message: String?,
                                    defaultValue: String?, result: JsPromptResult?): Boolean {
                if (result == null) return false
                if (nativeTrigger.trigger(message ?: "")) {
                    result.cancel()
                    return true
                }
                return false
            }
        }
    }

    companion object {
        /**
         * 创建 jBridge
         * @param webView 需要绑定的 WebView
         */
        @SuppressLint("SetJavaScriptEnabled")
        fun create(webView: WebView): JBridge {
            return webView.jBridge().apply {
                webView.settings.javaScriptEnabled = true
                webView.webChromeClient = chromeClient
            }
        }

        private fun WebView.jBridge(): JBridge {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                JBridge { evaluateJavascript(it, null) }
            } else {
                JBridge { loadUrl("javascript:$it") }
            }
        }
    }

    private class NativeTrigger(private val nativeInterface: BridgeImpl.Interface) {
        private val funcPattern = Pattern.compile("^jbridge://func.jbridge.app/([^/]+)/(.*)$")
        private val callbackPattern = Pattern.compile("^jbridge://callback.jbridge.app/([^/]+)/(.*)$")

        fun getInitJsCode(funcName: String): String {
            return "(function(){window.jBridge||function(){function a(h,i,j){$funcName('jbridge://'+h+'.jbridge.app/'+i+'/'+j)}var f={},g={};window.jBridge={register:function(h,i){g[h]=i},invoke:function(h,i,j){j&&(f[h]=j),a('func',h,i)},__func:function(h,i){var j=g[h];j&&j(i,function(){a('callback',h,i)})},__callback:function(h,i){var j=f[h];j&&j(i)}},console.log('hello, jBridge')}()})();"
        }

        fun trigger(uri: String): Boolean {
            return match(uri, funcPattern, nativeInterface::func)
                    || match(uri, callbackPattern, nativeInterface::callback)
        }

        private fun match(uri: String, pattern: Pattern, func: (String, String) -> Unit): Boolean {
            val matcher = pattern.matcher(uri)
            if (!matcher.matches()) return false
            val action = matcher.group(1)
            val param = matcher.group(2)
            func(action, param)
            return true
        }
    }
}