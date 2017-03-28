package org.chx.kdroid.kandy.etc

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Context.colorOf(resId: Int) = ContextCompat.getColor(this, resId)

fun View.colorOf(resId: Int) = context.colorOf(resId)

fun Context.inflateLayout(resId: Int, root: ViewGroup? = null, attachToRoot: Boolean = root != null): View =
        LayoutInflater.from(this).inflate(resId, root, attachToRoot)

fun ViewGroup.inflateLayout(resId: Int, attachToRoot: Boolean = true) =
        context.inflateLayout(resId, this, attachToRoot)




