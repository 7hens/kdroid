package cn.thens.kdroid.app

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import cn.thens.kdroid.KDroid
import cn.thens.logdog.Logdog
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 通过发送广播同来调用特定的事件。
 *
 * 命令行代码：
 *
 * ```shell
 * adb shell am broadcast -a $packageName.GLOBAL_BROADCAST -e data $command?$query
 * ```
 *
 * 可以通过执行下面的命令，查看所有已注册的命令信息：
 *
 * ```shell
 * adb shell am broadcast -a $packageName.GLOBAL_BROADCAST
 * ```
 *
 * > 注意：动态注册的命令需要及时销毁
 *
 * @author 7hens
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class GlobalBroadcast private constructor(private val context: Application) {
    private val action = context.packageName + ".GLOBAL_BROADCAST"
    private val extraName = "data"
    private val isInitialized = AtomicBoolean(false)
    private val callbacks = ConcurrentHashMap<String, (String) -> Unit>()
    private val records = ConcurrentHashMap<String, String>()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != action) return
            resolveUri(intent.getStringExtra(extraName) ?: "")
        }

        fun resolveUri(data: String) {
            try {
                var command = data
                var query = ""
                val queryIndex = data.indexOf("?")
                if (queryIndex >= 0) {
                    command = data.substring(0, queryIndex)
                    query = data.substring(queryIndex + 1)
                }
                callbacks[command]?.invoke(query)
            } catch (e: Throwable) {
                Logdog.get().error(e)
            }
        }
    }

    fun register(command: String, callback: (String) -> Unit) {
        initialize(context)
        if (isInitialized.get()) {
            val record = getCurrentCommandRecord()
            callbacks[command] = { callback(it) }
            records[command] = record
        }
        throw RuntimeException("Broadcast is uninitialized")
    }

    fun unregister(command: String) {
        callbacks.remove(command)
        records.remove(command)
    }

    fun send(uri: String) {
        initialize(context)
        val intent = Intent()
        intent.action = action
        intent.putExtra(extraName, uri)
        context.sendBroadcast(intent)
    }

    private fun initialize(context: Context) {
        if (!isInitialized.compareAndSet(false, true)) return
        context.registerReceiver(receiver, IntentFilter(action))
        registerDefaultCommands()
    }

    private fun getCurrentCommandRecord(): String {
        val stackTrace = Thread.currentThread().stackTrace
        var isCurrentClass = false
        for (traceElement in stackTrace) {
            val className = traceElement.className
            val fileName = traceElement.fileName
            val lineNumber = traceElement.lineNumber
            if (GlobalBroadcast::class.java.name == className) {
                isCurrentClass = true
            } else if (isCurrentClass) {
                return String.format(Locale.getDefault(), "(%s:%d)", fileName, lineNumber)
            }
        }
        return ""
    }

    private fun registerDefaultCommands() {
        register("") {
            val buffer = StringBuilder()
            for ((command, record) in records) {
                buffer.append(command).append(" ").append(record).append("\n")
            }
            Logdog.get().debug(buffer.toString())
        }
    }

    companion object {
        val instance by lazy { GlobalBroadcast(KDroid.app) }

        fun get() = instance
    }
}