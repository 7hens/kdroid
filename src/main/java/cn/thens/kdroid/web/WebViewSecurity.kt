package cn.thens.kdroid.web


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView

object WebViewSecurity {
    @SuppressLint("SetJavaScriptEnabled")
    fun init(webView: WebView) {
        webView.apply {
            setBackgroundColor(Color.TRANSPARENT)
            removeJavascriptInterface("searchBoxJavaBridge_")
            removeJavascriptInterface("accessibility")
            removeJavascriptInterface("accessibilityTraversal")
            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
                allowFileAccess = false
                allowContentAccess = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    allowFileAccessFromFileURLs = false
                    allowUniversalAccessFromFileURLs = false
                }
                setAppCacheEnabled(true)
                blockNetworkImage = false
                domStorageEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = true
                databaseEnabled = true
                @Suppress("DEPRECATION")
                savePassword = false
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    fun destroy(webView: WebView) {
        webView.apply {
            (parent as? ViewGroup)?.removeView(webView)
            stopLoading()
            settings.javaScriptEnabled = false
            clearHistory()
            loadUrl("about:blank")
            removeAllViews()
            destroy()
        }
    }
}
