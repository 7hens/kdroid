package org.chx.kdroid.jadapter.delegate

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class AdapterDelegate<D>(dataList: List<D>) : ViewHolder.Factory<D>(dataList) {
    private val holderList = HashMap<Int, ViewHolder<D>>()

    fun getViewHolder(container: ViewGroup, position: Int): ViewHolder<D> {
        return holderList[position] ?: createViewHolder(container, position).apply { holderList[position] = this }
    }

    fun getViewHolder(position: Int) = holderList[position]

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

    companion object {
        fun <D> singleLayout(dataList: List<D>, @LayoutRes layoutRes: Int, holderFunc: (View) -> ViewHolder<D>): AdapterDelegate<D> {
            return object : AdapterDelegate<D>(dataList) {
                override fun createViewHolder(container: ViewGroup, position: Int): ViewHolder<D> {
                    return holderFunc(LayoutInflater.from(container.context).inflate(layoutRes, container, false))
                }
            }
        }
    }
}