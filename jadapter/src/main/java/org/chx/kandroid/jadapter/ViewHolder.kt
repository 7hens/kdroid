package org.chx.kandroid.jadapter

import android.view.View

abstract class ViewHolder<in D>(val itemView: View) {
    var position: Int = 0
        internal set

    abstract fun convert(data: D)
}