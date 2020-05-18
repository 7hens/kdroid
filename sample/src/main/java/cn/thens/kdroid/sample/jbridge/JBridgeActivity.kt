package cn.thens.kdroid.sample.jbridge

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.FrameLayout
import cn.thens.kdroid.app.toast
import cn.thens.kdroid.extra.ActivityIntentOptions
import cn.thens.kdroid.web.JBridge
import cn.thens.kdroid.web.WebViewSecurity
import cn.thens.kdroid.util.Logdog

class JBridgeActivity : Activity() {
    private val url get() = "file:///android_asset/jbridge.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView(this))
    }

    private fun createContentView(context: Context): View {
        val wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT
        return FrameLayout(context).apply {
            val web = WebView(context).apply {
                WebViewSecurity.init(this)
                val jBridge = JBridge.create(this)
                jBridge.register("toast") { arg, callback ->
                    toast(arg)
                    Logdog.debug(arg)
                    callback("toast.callback")
                }
            }.also { addView(it) }

            Button(context).apply {
                text = "refresh"
                setOnClickListener { web.loadUrl(url) }
                layoutParams = ViewGroup.LayoutParams(wrapContent, wrapContent)
            }.also { addView(it) }
        }
    }

    companion object : ActivityIntentOptions<Companion>()
}