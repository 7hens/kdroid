package cn.thens.kdroid.sample.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import cn.thens.activity_request.ActivityIntentOptions

@Suppress("unused", "UNUSED_VARIABLE")
class IntentExtraActivity : Activity() {

    fun parseIntent() {
        val name = intent.name
        val count = intent.count
        val phone = intent.phone
    }

    fun startActivity(context: Context) {
        val intent = Intent()
        context.startActivity(IntentExtraActivity.intent(context) {
            it.name = "hello"
            it.count = 2
            it.phone = "1888888"

        })
    }

    companion object : ActivityIntentOptions<Companion> {
        var Intent.name by string()
        var Intent.count by int()
        var Intent.phone by string()
    }
}