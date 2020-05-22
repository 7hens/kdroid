package cn.thens.kdroid.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author 7hens
 */
open class HomeKey(private val context: Context) {
    private val isRegistered = AtomicBoolean(false)

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        val EXTRA_REASON = "reason"
        val REASON_HOME_KEY = "homekey"

        override fun onReceive(context: Context, intent: Intent) {
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS == intent.action &&
                    REASON_HOME_KEY == intent.getStringExtra(EXTRA_REASON)) {
                onPressed()
            }
        }
    }

    fun register() {
        if (isRegistered.compareAndSet(false, true)) {
            val intentFilter = IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            context.registerReceiver(receiver, intentFilter)
        }
    }

    fun unregister() {
        if (isRegistered.compareAndSet(true, false)) {
            context.unregisterReceiver(receiver)
        }
    }

    protected open fun onPressed() {}

    companion object {
        inline fun bind(context: Context, lifecycle: Lifecycle, crossinline callback: () -> Unit) {
            lifecycle.addObserver(object : LifecycleObserver {
                private val homeKey: HomeKey = object : HomeKey(context) {
                    override fun onPressed() {
                        callback.invoke()
                    }
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                fun onShow() {
                    homeKey.register()
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                fun onDismiss() {
                    homeKey.unregister()
                }
            })
        }

        inline fun bind(fragment: Fragment, crossinline callback: () -> Unit) {
            bind(fragment.requireContext(), fragment.lifecycle, callback)
        }

        inline fun bind(activity: ComponentActivity, crossinline callback: () -> Unit) {
            bind(activity, activity.lifecycle, callback)
        }
    }

}
