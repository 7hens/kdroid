package org.chx.kdroid.kandy.etc

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.toMillis() = this * 1000L

fun Long.toDateString(format: String = "yyyy-MM-dd HH:mm:ss") = SimpleDateFormat(format, Locale.CHINA).format(Date(this))!!

fun Long.toCalendar() = Calendar.getInstance().let { it.timeInMillis = this }

