@file:Suppress("unused")

package cn.thens.kdroid.core.util

import android.content.Context
import android.util.TypedValue
import android.view.View

private fun Context.px(value: Number, unit: Int): Int = TypedValue.applyDimension(unit, value.toFloat(), displayMetrics).toInt()

fun Context.dp(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_DIP)
fun Context.sp(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_SP)
fun Context.pt(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_PT)
fun Context.inch(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_IN)
fun Context.mm(value: Number): Int = px(value, TypedValue.COMPLEX_UNIT_MM)

fun View.dp(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_DIP)
fun View.sp(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_SP)
fun View.pt(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_PT)
fun View.inch(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_IN)
fun View.mm(value: Number): Int = context.px(value, TypedValue.COMPLEX_UNIT_MM)