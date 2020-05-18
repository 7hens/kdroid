@file:Suppress("unused")

package cn.thens.kdroid.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Environment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.io.File

fun Activity.hideSoftInput() {
    val focusToken = currentFocus?.windowToken
    val inputMethodManager = SystemServices.inputMethod(this)
    if (focusToken != null && inputMethodManager != null) {
        inputMethodManager.hideSoftInputFromWindow(focusToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Context.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

val hasExternalStorage: Boolean get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

val Context.suitableCacheDir: File get () = if (hasExternalStorage) externalCacheDir!! else cacheDir

fun Context.extractActivity(): Activity? {
    var ctx = this
    while (true) {
        when (ctx) {
            is Activity -> return ctx
            is Application -> return null
            is ContextWrapper -> {
                val baseContext = ctx.baseContext
                if (baseContext == ctx) return null
                ctx = baseContext
            }
            else -> return null
        }
    }
}