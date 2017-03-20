package org.chx.kdroid.jadapter.delegate

import android.view.View
import android.view.ViewGroup

abstract class ViewHolder<in D>(val itemView: View) {
    abstract fun convert(position: Int, data: D)

    abstract class Factory<D>(dataList: List<D>) : List<D> by dataList {
        abstract fun createViewHolder(container: ViewGroup, position: Int): ViewHolder<D>

        fun bindViewHolder(holder: ViewHolder<D>, position: Int) {
            holder.convert(position, this[position])
        }
    }
}