package cn.thens.kdroid.core.app

import android.os.Handler
import android.os.Looper

@Suppress("unused", "MemberVisibilityCanBePrivate")
object CrashKiller {
    private val defaultHandler by lazy { Thread.getDefaultUncaughtExceptionHandler() }
    private val handlerList = ArrayList<Thread.UncaughtExceptionHandler>()
    private val crashHandler by lazy {
        Thread.UncaughtExceptionHandler { thread, e ->
            handlerList.forEach { it.uncaughtException(thread, e) }
        }
    }

    val finishTopActivityOnError by lazy {
        Thread.UncaughtExceptionHandler { thread, _ ->
            if (thread == Looper.getMainLooper().thread) {
                TopActivity.optional()?.finish()
            }
        }
    }

    fun addUncaughtExceptionHandler(handler: Thread.UncaughtExceptionHandler) {
        if (handlerList.contains(handler).not()) handlerList.add(handler)
        mount()
    }

    fun removeUncaughtExceptionHandler(handler: Thread.UncaughtExceptionHandler) {
        handlerList.remove(handler)
        if (handlerList.isEmpty()) unmount()
    }

    fun mount() {
        if (Thread.getDefaultUncaughtExceptionHandler() == defaultHandler) {
            Handler(Looper.getMainLooper()).post {
                while (true) {
                    try {
                        Looper.loop()
                    } catch (e: Throwable) {
                        if (e is ToQuitException) break
                        crashHandler.uncaughtException(Thread.currentThread(), e)
                    }
                }
            }
            Thread.setDefaultUncaughtExceptionHandler(crashHandler)
        }
    }

    @Suppress("SpellCheckingInspection")
    fun unmount() {
        if (Thread.getDefaultUncaughtExceptionHandler() == crashHandler) {
            Thread.setDefaultUncaughtExceptionHandler(defaultHandler)
            Handler(Looper.getMainLooper()).post { throw ToQuitException() }
        }
    }

    private class ToQuitException : RuntimeException()
}