package cn.thens.kdroid.extra

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi

/**
 * @author 7hens
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
open class ActivityIntentOptions<out IntentOptions> : CompanionIntentOptions<IntentOptions>() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun start(context: Context, bundle: Bundle?, configure: IntentOptions.(Intent) -> Unit) {
        context.startActivity(intent(context, configure), bundle)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun start(context: Context, bundle: Bundle?) {
        start(context, bundle) {}
    }

    fun start(context: Context, configure: IntentOptions.(Intent) -> Unit) {
        context.startActivity(intent(context, configure))
    }

    fun start(context: Context) {
        start(context) {}
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun startForResult(context: Activity, requestCode: Int, bundle: Bundle?, configure: IntentOptions.(Intent) -> Unit) {
        context.startActivityForResult(intent(context, configure), requestCode, bundle)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun startForResult(context: Activity, requestCode: Int, bundle: Bundle?) {
        startForResult(context, requestCode, bundle) {}
    }

    fun startForResult(context: Activity, requestCode: Int, configure: IntentOptions.(Intent) -> Unit) {
        context.startActivityForResult(intent(context, configure), requestCode)
    }

    fun startForResult(context: Activity, requestCode: Int) {
        startForResult(context, requestCode) {}
    }
}