package cn.thens.kdroid.core.extra

import android.content.ComponentName
import android.content.Context
import android.content.Intent

/**
 * @author 7hens
 */
open class CompanionIntentOptions<out IntentOptions> : IntentExtra {
    private val className: String by lazy { javaClass.name.replace("\$Companion", "") }

    @Suppress("UNCHECKED_CAST")
    private val intentOptions: IntentOptions by lazy { this as IntentOptions }

    fun intent(context: Context, configure: IntentOptions.(Intent) -> Unit): Intent {
        return Intent().apply {
            component = ComponentName(context.packageName, className)
            configure(intentOptions, this)
        }
    }
}