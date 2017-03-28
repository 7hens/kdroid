package org.chx.kdroid.kandy.view

import android.view.View
import android.view.ViewGroup

fun View.removeFromParent() = (parent as? ViewGroup)?.removeView(this)

fun <V : View> V.onClick(func: V.() -> Unit) = setOnClickListener { this.func() }

