package org.chx.kdroid.kandy.etc

import android.content.Context
import android.widget.Toast

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, duration).show()

fun Context.longToast(text: CharSequence) = toast(text, Toast.LENGTH_LONG)