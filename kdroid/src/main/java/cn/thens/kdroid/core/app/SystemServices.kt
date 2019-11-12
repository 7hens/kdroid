package cn.thens.kdroid.core.app

import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.content.ClipboardManager
import android.content.Context
import android.hardware.SensorManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.PowerManager
import android.os.storage.StorageManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.inputmethod.InputMethodManager

@Suppress("unused")
object SystemServices {
    @Suppress("UNCHECKED_CAST")
    fun <T> of(context: Context, name: String): T? {
        return context.getSystemService(name) as? T
    }

    fun accessibility(context: Context): AccessibilityManager? {
        return of(context, Context.ACCESSIBILITY_SERVICE)
    }

    fun account(context: Context): AccountManager? {
        return of(context, Context.ACCOUNT_SERVICE)
    }

    fun activity(context: Context): ActivityManager? {
        return of(context, Context.ACTIVITY_SERVICE)
    }

    fun alarm(context: Context): AlarmManager? {
        return of(context, Context.ALARM_SERVICE)
    }

    fun audio(context: Context): AudioManager? {
        return of(context, Context.AUDIO_SERVICE)
    }

    fun clipboard(context: Context): ClipboardManager? {
        return of(context, Context.CLIPBOARD_SERVICE)
    }

    fun connectivity(context: Context): ConnectivityManager? {
        return of(context, Context.CONNECTIVITY_SERVICE)
    }

    fun devicePolicy(context: Context): DevicePolicyManager? {
        return of(context, Context.DEVICE_POLICY_SERVICE)
    }

    fun download(context: Context): DownloadManager? {
        return of(context, Context.DOWNLOAD_SERVICE)
    }

    fun inputMethod(context: Context): InputMethodManager? {
        return of(context, Context.INPUT_METHOD_SERVICE)
    }

    fun keyguard(context: Context): KeyguardManager? {
        return of(context, Context.KEYGUARD_SERVICE)
    }

    fun layoutInflater(context: Context): LayoutInflater? {
        return of(context, Context.LAYOUT_INFLATER_SERVICE)
    }

    fun location(context: Context): LocationManager? {
        return of(context, Context.LOCATION_SERVICE)
    }

    fun nfcManager(context: Context): NfcManager? {
        return of(context, Context.NFC_SERVICE)
    }

    fun notification(context: Context): NotificationManager? {
        return of(context, Context.NOTIFICATION_SERVICE)
    }

    fun power(context: Context): PowerManager? {
        return of(context, Context.POWER_SERVICE)
    }

    fun search(context: Context): SearchManager? {
        return of(context, Context.SEARCH_SERVICE)
    }

    fun sensor(context: Context): SensorManager? {
        return of(context, Context.SENSOR_SERVICE)
    }

    fun storage(context: Context): StorageManager? {
        return of(context, Context.STORAGE_SERVICE)
    }

    fun telephony(context: Context): TelephonyManager? {
        return of(context, Context.TELEPHONY_SERVICE)
    }

    fun uiMode(context: Context): UiModeManager? {
        return of(context, Context.UI_MODE_SERVICE)
    }

    fun usb(context: Context): UsbManager? {
        return of(context, Context.USB_SERVICE)
    }

    fun wallpaper(context: Context): WallpaperManager? {
        return of(context, Context.WALLPAPER_SERVICE)
    }

    fun wifi(context: Context): WifiManager? {
        return of(context, Context.WIFI_SERVICE)
    }

    fun wifiP2p(context: Context): WifiP2pManager? {
        return of(context, Context.WIFI_P2P_SERVICE)
    }

    fun window(context: Context): WindowManager? {
        return of(context, Context.WINDOW_SERVICE)
    }
}