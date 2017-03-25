package org.chx.kdroid.candy.context

import android.app.Service
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

fun <S : Service> Context.startService(cls: KClass<S>, func: Intent.() -> Unit = {}) {
    startService(Intent(this, cls.java).apply { func() })
}
