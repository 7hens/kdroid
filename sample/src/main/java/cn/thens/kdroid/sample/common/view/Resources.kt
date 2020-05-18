package cn.thens.kdroid.sample.common.view

import android.app.Fragment
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import android.view.View
import cn.thens.kdroid.util.IdGenerator

fun Context.color(@ColorRes colorId: Int): Int = ContextCompat.getColor(this, colorId)
fun Context.colorStateList(@ColorRes colorId: Int): ColorStateList? = ContextCompat.getColorStateList(this, colorId)
fun Context.drawable(@DrawableRes drawableId: Int): Drawable? = ContextCompat.getDrawable(this, drawableId)
fun Context.string(@StringRes stringId: Int): String = getString(stringId)

fun View.color(@ColorRes colorId: Int): Int = context.color(colorId)
fun View.colorStateList(@ColorRes colorId: Int): ColorStateList? = context.colorStateList(colorId)
fun View.drawable(@DrawableRes drawableId: Int): Drawable? = context.drawable(drawableId)
fun View.string(@StringRes stringId: Int): String = context.string(stringId)
fun View.generateId(): Int = IdGenerator.generateId().also { id = it }

fun Fragment.color(@ColorRes colorId: Int): Int = activity.color(colorId)
fun Fragment.colorStateList(@ColorRes colorId: Int): ColorStateList? = activity.colorStateList(colorId)
fun Fragment.drawable(@DrawableRes drawableId: Int): Drawable? = activity.drawable(drawableId)
fun Fragment.string(@StringRes stringId: Int): String = activity.string(stringId)