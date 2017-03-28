package org.chx.kdroid.kandy.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Context.inflateLayout(@LayoutRes resId: Int, root: ViewGroup? = null, attachToRoot: Boolean = root != null): View
        = LayoutInflater.from(this).inflate(resId, root, attachToRoot)

fun ViewGroup.inflateLayout(@LayoutRes resId: Int, attachToRoot: Boolean = true): View
        = context.inflateLayout(resId, this, attachToRoot)
