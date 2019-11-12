package cn.thens.kdroid.core.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.inputmethod.InputMethodManager

@Suppress("unused")
object AndroidLeakFixer {
    fun fix(context: Application) {
        fixLeakOfUserManager(context)
        context.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit
            override fun onActivityStarted(activity: Activity) = Unit
            override fun onActivityResumed(activity: Activity) = Unit
            override fun onActivityPaused(activity: Activity) = Unit
            override fun onActivityStopped(activity: Activity) = Unit
            override fun onActivitySaveInstanceState(activity: Activity, savedInstanceState: Bundle?) = Unit
            override fun onActivityDestroyed(activity: Activity) {
                fixLeakOfInputMethodManager(activity)
            }
        })
    }

    private fun fixLeakOfUserManager(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.packageManager.getUserBadgedLabel("", android.os.Process.myUserHandle())
        }
    }

    private fun fixLeakOfInputMethodManager(context: Activity) {
        try {
            val imm = SystemServices.inputMethod(context.applicationContext)!!
            InputMethodManager::class.java
                    .getDeclaredMethod("windowDismissed", IBinder::class.java)
                    .invoke(imm, context.window.decorView.windowToken)

//            for (fieldName in arrayOf("mLastSrvView", "mCurRootView", "mServedView", "mNextServedView")) {
//                try {
//                    val field = imm.javaClass.getDeclaredField(fieldName)
//                    field.isAccessible = true
//                    val view = field.get(imm) as? View
//                    if (view == null || view.context != context) break
//                    field.set(imm, null)
//                } catch (t: Throwable) {
//                    t.printStackTrace()
//                }
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}