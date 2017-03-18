package org.chx.kdroid.jadapter.proxy

import android.view.View
import android.view.ViewGroup

abstract class ViewHolder<in D>(val itemView: View) {
    abstract fun convert(position: Int, data: D)

    abstract class Factory<D>(dataList: List<D>) : List<D> by dataList {
        private val holderList = HashMap<Int, ViewHolder<D>>()

        abstract fun createViewHolder(container: ViewGroup, position: Int): ViewHolder<D>

        fun getViewHolder(container: ViewGroup, position: Int): ViewHolder<D> {
            val realPos = position % size
            return holderList[realPos] ?: createViewHolder(container, realPos).apply { holderList[realPos] = this }
        }

        fun getView(container: ViewGroup, position: Int): View {
            return getViewHolder(container, position).let {
                bindViewHolder(it, position)
                it.itemView.apply { (parent as? ViewGroup)?.removeView(this) }
            }
        }

        fun bindViewHolder(holder: ViewHolder<D>, position: Int) {
            holder.convert(position, this[position % size])
        }

        fun removeViewHolder(position: Int): ViewHolder<D>? {
            val realPos = position % size
            val viewHolder = holderList[realPos]
            val itemView = viewHolder?.itemView
            if (itemView != null && itemView.visibility != View.VISIBLE) {
                (itemView.parent as? ViewGroup)?.removeView(itemView)
                holderList.remove(realPos)
            }
            return viewHolder
        }
    }
}