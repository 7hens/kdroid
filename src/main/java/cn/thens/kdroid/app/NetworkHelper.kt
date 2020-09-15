package cn.thens.kdroid.app

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author 7hens
 */
object NetworkHelper {
    const val TYPE_UNKNOWN = 0
    const val TYPE_NONE = 1
    const val TYPE_2G = 2
    const val TYPE_3G = 3
    const val TYPE_4G = 4
    const val TYPE_WIFI = 11

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun getType(context: Context): Int {
        val connectivityManager = SystemServices.connectivity(context)
        val networkInfo = connectivityManager?.activeNetworkInfo ?: return TYPE_NONE
        if (!networkInfo.isConnected || !networkInfo.isAvailable) return TYPE_NONE
        return when (networkInfo.type) {
            ConnectivityManager.TYPE_DUMMY,
            ConnectivityManager.TYPE_WIFI,
            ConnectivityManager.TYPE_WIMAX -> TYPE_WIFI
            ConnectivityManager.TYPE_MOBILE,
            ConnectivityManager.TYPE_MOBILE_DUN,
            ConnectivityManager.TYPE_MOBILE_SUPL,
            ConnectivityManager.TYPE_MOBILE_MMS,
            ConnectivityManager.TYPE_MOBILE_HIPRI, 11 -> {
                val telephonyManager = SystemServices.telephony(context)!!
                when (telephonyManager.networkType) {
                    TelephonyManager.NETWORK_TYPE_LTE -> TYPE_4G
                    TelephonyManager.NETWORK_TYPE_UMTS,
                    TelephonyManager.NETWORK_TYPE_EVDO_0,
                    TelephonyManager.NETWORK_TYPE_EVDO_A,
                    TelephonyManager.NETWORK_TYPE_HSDPA,
                    TelephonyManager.NETWORK_TYPE_HSUPA,
                    TelephonyManager.NETWORK_TYPE_HSPA,
                    TelephonyManager.NETWORK_TYPE_EVDO_B,
                    TelephonyManager.NETWORK_TYPE_EHRPD,
                    TelephonyManager.NETWORK_TYPE_HSPAP, 17 -> TYPE_3G
                    TelephonyManager.NETWORK_TYPE_GPRS,
                    TelephonyManager.NETWORK_TYPE_EDGE,
                    TelephonyManager.NETWORK_TYPE_CDMA,
                    TelephonyManager.NETWORK_TYPE_1xRTT,
                    TelephonyManager.NETWORK_TYPE_IDEN,
                    TelephonyManager.NETWORK_TYPE_UNKNOWN -> TYPE_2G
                    else -> TYPE_2G
                }
            }
            ConnectivityManager.TYPE_ETHERNET -> TYPE_UNKNOWN
            else -> TYPE_UNKNOWN
        }
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun isConnected(context: Context): Boolean {
        val connectivityManager = SystemServices.connectivity(context) ?: return false
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun listen(context: Context, lifecycle: Lifecycle, callback: (isConnected: Boolean) -> Unit) {
        object : Receiver(context) {
            override fun onNetworkConnectionChanged(isConnected: Boolean) {
                super.onNetworkConnectionChanged(isConnected)
                callback.invoke(isConnected)
            }
        }.bind(lifecycle)
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun listen(fragment: Fragment, callback: (isConnected: Boolean) -> Unit) {
        listen(fragment.requireContext(), fragment.lifecycle, callback)
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun listen(activity: ComponentActivity, callback: (isConnected: Boolean) -> Unit) {
        listen(activity, activity.lifecycle, callback)
    }

    @SuppressLint("MissingPermission")
    private open class Receiver(private val context: Context) : BroadcastReceiver() {
        private var lastNetworkConnected = false
        override fun onReceive(context: Context, intent: Intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
                val isNetworkConnected = isConnected(context)
                if (isNetworkConnected != lastNetworkConnected) {
                    lastNetworkConnected = isNetworkConnected
                    onNetworkConnectionChanged(isNetworkConnected)
                }
            }
        }

        protected open fun onNetworkConnectionChanged(isConnected: Boolean) {}

        fun register() {
            lastNetworkConnected = isConnected(context)
            onNetworkConnectionChanged(lastNetworkConnected)
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
            intentFilter.addAction("android.net.wifi.STATE_CHANGE")
            context.registerReceiver(this, intentFilter)
        }

        fun unregister() {
            context.unregisterReceiver(this)
        }

        fun bind(lifecycle: Lifecycle) {
            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                fun onCreate() {
                    register()
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    unregister()
                }
            })
        }

    }
}
