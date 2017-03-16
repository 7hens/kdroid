package org.chx.kdroid.jadapter

import android.view.View

abstract class ViewHolder<in D>(val itemView: View) {
    var position: Int = 0
        internal set

    abstract fun convert(data: D)
}