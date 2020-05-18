@file:Suppress("unused")

package cn.thens.kdroid.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import android.util.DisplayMetrics
import android.view.View

fun Context.colorOf(@ColorRes resId: Int): Int = ContextCompat.getColor(this, resId)
fun Context.colorStateListOf(@ColorRes resId: Int): ColorStateList? = ContextCompat.getColorStateList(this, resId)
fun Context.drawableOf(@DrawableRes resId: Int): Drawable? = ContextCompat.getDrawable(this, resId)
fun Context.stringOf(@StringRes resId: Int): String = getString(resId)
fun Context.dimenOf(@DimenRes resId: Int): Int = resources.getDimensionPixelSize(resId)
fun Context.integerOf(@IntegerRes resId: Int): Int = resources.getInteger(resId)
inline val Context.displayMetrics: DisplayMetrics get() = resources.displayMetrics

fun View.colorOf(@ColorRes resId: Int): Int = context.colorOf(resId)
fun View.colorStateListOf(@ColorRes resId: Int): ColorStateList? = context.colorStateListOf(resId)
fun View.drawableOf(@DrawableRes resId: Int): Drawable? = context.drawableOf(resId)
fun View.stringOf(@StringRes resId: Int): String = context.stringOf(resId)
fun View.dimenOf(@DimenRes resId: Int): Int = context.dimenOf(resId)
fun View.integerOf(@IntegerRes resId: Int): Int = context.integerOf(resId)
inline val View.displayMetrics: DisplayMetrics get() = context.displayMetrics