package org.chx.kdroid.jadapter.delegate

import android.view.View
import android.view.ViewGroup

abstract class ViewHolder<in D>(val itemView: View) {
    abstract fun convert(data: D, position: Int)

    abstract class Factory<D>(dataList: List<D>) : List<D> by dataList {
        abstract fun createViewHolder(container: ViewGroup, position: Int): ViewHolder<D>

        fun bindViewHolder(holder: ViewHolder<D>, position: Int) {
            holder.convert(this[position], position)
        }
    }
}