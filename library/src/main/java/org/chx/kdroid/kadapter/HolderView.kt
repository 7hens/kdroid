package org.chx.kdroid.kadapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

abstract class HolderView<in D>(itemView: View) : LinearLayout(itemView.context) {
    init {
        this.addView(itemView)
    }

    abstract fun convert(data: D, position: Int)

    abstract class Factory<D>(dataList: List<D>) : List<D> by dataList {
        abstract fun createView(container: ViewGroup, position: Int): HolderView<D>

        fun bindView(holder: HolderView<D>, position: Int) {
            holder.convert(this[position], position)
        }
    }
}