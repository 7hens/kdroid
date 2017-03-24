package org.chx.kdroid.candy.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Context.inflate(@LayoutRes resId: Int, root: ViewGroup? = null, attachToRoot: Boolean = root != null): View
        = LayoutInflater.from(this).inflate(resId, root, attachToRoot)

fun ViewGroup.inflate(@LayoutRes resId: Int, attachToRoot: Boolean = true): View
        = context.inflate(resId, this, attachToRoot)
