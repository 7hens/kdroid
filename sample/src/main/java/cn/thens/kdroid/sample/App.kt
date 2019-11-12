package cn.thens.kdroid.sample

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import cn.thens.kdroid.core.KDroid
import cn.thens.kdroid.core.app.CrashKiller

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KDroid.initialize(this)
        CrashKiller.addUncaughtExceptionHandler(CrashKiller.finishTopActivityOnError)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}