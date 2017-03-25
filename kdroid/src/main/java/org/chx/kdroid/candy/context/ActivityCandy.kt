package org.chx.kdroid.candy.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass


fun <A : Activity> Context.startActivity(cls: KClass<A>, func: Intent.() -> Unit = {}) {
    startActivity(Intent(this, cls.java).apply { func() })
}
