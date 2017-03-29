package org.chx.kdroid.kadapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.chx.kdroid.kandy.etc.inflateLayout

abstract class HolderView<in D>(itemView: View) : LinearLayout(itemView.context) {
    init {
        setMeasuredDimension(itemView.measuredWidth, itemView.measuredHeight)
        layoutParams = itemView.layoutParams
        this.addView(itemView)
    }

    constructor(container: ViewGroup, layoutRes: Int) : this(container.inflateLayout(layoutRes, false))

    abstract fun convert(data: D, position: Int)

    abstract class Factory<D>(dataList: List<D>) : List<D> by dataList {
        abstract fun createView(container: ViewGroup, position: Int): HolderView<D>

        fun bindView(holder: HolderView<D>, position: Int) {
            holder.convert(this[position], position)
        }
    }
}