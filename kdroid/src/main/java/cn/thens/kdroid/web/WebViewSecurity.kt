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
        webView.setBackgroundColor(Color.TRANSPARENT)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        settings.allowFileAccess = false
        settings.allowContentAccess = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.allowFileAccessFromFileURLs = false
            settings.allowUniversalAccessFromFileURLs = false
        }
        settings.setAppCacheEnabled(true)
        settings.blockNetworkImage = false
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.databaseEnabled = true
        @Suppress("DEPRECATION")
        settings.savePassword = false
        webView.removeJavascriptInterface("searchBoxJavaBridge_")
        webView.removeJavascriptInterface("accessibility")
        webView.removeJavascriptInterface("accessibilityTraversal")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    fun destroy(webView: WebView) {
        (webView.parent as? ViewGroup)?.removeView(webView)
        webView.stopLoading()
        webView.settings.javaScriptEnabled = false
        webView.clearHistory()
        webView.loadUrl("about:blank")
        webView.removeAllViews()
        webView.destroy()
    }
}
