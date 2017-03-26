package org.chx.kdroid.kandy.view

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.StyleableRes
import android.util.AttributeSet

fun AttributeSet.resolve (context: Context, @StyleableRes attrs:IntArray, func: TypedArray.()->Unit) {
    context.obtainStyledAttributes(this, attrs).apply { func() }.recycle()
}