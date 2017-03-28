package org.chx.kdroid.kandy.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

fun <A : Activity> KClass<A>.start(context: Context, func: Intent.() -> Unit = {}) {
    context.startActivity(Intent(context, java).apply { func() })
}
