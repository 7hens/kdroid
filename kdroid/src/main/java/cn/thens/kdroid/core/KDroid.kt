package cn.thens.kdroid.core

import android.app.Application
import android.content.Context
import cn.thens.kdroid.core.app.TopActivity
import java.util.concurrent.atomic.AtomicBoolean

object KDroid {
    private val isInitialized = AtomicBoolean(false)

    val debug: Boolean by lazy {
        try {
            val cBuildConfig = Class.forName(app.packageName + ".BuildConfig")
            cBuildConfig.getField("DEBUG").get(null) as Boolean
        } catch (e: Exception) {
            false
        }
    }

    val app get() = application

    private lateinit var application: Application

    fun initialize(context: Context) {
        if (!isInitialized.compareAndSet(false, true)) return
        application = context.applicationContext as Application
        TopActivity.initialize(application)
    }
}