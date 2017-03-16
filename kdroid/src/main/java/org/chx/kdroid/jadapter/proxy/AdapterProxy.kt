package org.chx.kdroid.jadapter.proxy

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.chx.kdroid.jadapter.ViewHolder


abstract class AdapterProxy<D>(dataList: List<D>) : ViewHolderProvider<D>(dataList) {
    open fun getViewType(position: Int): Int = 0

    abstract fun getLayoutRes(viewType: Int): Int

    abstract fun getViewHolder(itemView: View): ViewHolder<D>

    override fun getViewHolder(parent: ViewGroup, position: Int): ViewHolder<D> {
        return getViewHolderWithViewType(parent, getViewType(position))
    }

    fun getViewHolderWithViewType(parent: ViewGroup, viewType: Int): ViewHolder<D> {
        return getViewHolderWithLayoutRes(parent, getLayoutRes(viewType))
    }

    fun getViewHolderWithLayoutRes(parent: ViewGroup, layoutRes: Int): ViewHolder<D> {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return getViewHolder(itemView)
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