package cn.thens.kdroid.sample.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import cn.thens.kdroid.extra.CompanionIntentOptions
import cn.thens.kdroid.extra.IntentExtra

@Suppress("unused", "UNUSED_VARIABLE")
class IntentExtraActivity : Activity() {

    fun parseIntent() {
        val name = intent.name
        val count = intent.count
        val phone = intent.phone
    }

    fun startActivity(context: Context) {
        context.startActivity(IntentExtraActivity.intent(context) {
            it.name = "hello"
            it.count = 2
            it.phone = "1888888"

        })
    }

    companion object : CompanionIntentOptions<Companion>() {
        var Intent.name by IntentExtra.string()
        var Intent.count by IntentExtra.int()
        var Intent.phone by IntentExtra.string()
    }
}