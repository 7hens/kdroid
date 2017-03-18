package org.chx.kdroid.jadapter.proxy

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class AdapterProxy<D>(dataList: List<D>) : ViewHolder.Factory<D>(dataList) {
    open fun getViewType(position: Int): Int = 0

    abstract fun getLayoutRes(viewType: Int): Int

    abstract fun getViewHolder(itemView: View): ViewHolder<D>

    override fun createViewHolder(container: ViewGroup, position: Int): ViewHolder<D> {
        return createViewHolderWithViewType(container, getViewType(position))
    }

    fun createViewHolderWithViewType(container: ViewGroup, viewType: Int): ViewHolder<D> {
        return createViewHolderWithLayoutRes(container, getLayoutRes(viewType))
    }

    fun createViewHolderWithLayoutRes(container: ViewGroup, layoutRes: Int): ViewHolder<D> {
        return getViewHolder(LayoutInflater.from(container.context).inflate(layoutRes, container, false))
    }

    companion object {
        fun <D> singleLayout(dataList: List<D>, @LayoutRes layoutRes: Int, holderFunc: (View) -> ViewHolder<D>): AdapterProxy<D> {
            return object : AdapterProxy<D>(dataList) {
                override fun getLayoutRes(viewType: Int) = layoutRes

                override fun getViewHolder(itemView: View): ViewHolder<D> = holderFunc(itemView)
            }
        }
    }
}