package org.chx.kdroid.kandy.context

import android.app.Service
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

fun <S : Service> KClass<S>.start(context: Context, func: Intent.() -> Unit = {}) {
    context.startService(Intent(context, java).apply { func() })
}
