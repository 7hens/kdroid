package org.chx.kdroid.jadapter.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import org.chx.kdroid.jadapter.delegate.AdapterDelegate

class YaPagerAdapter<D>(val delegate: AdapterDelegate<D>, val boundless: Boolean = false) : PagerAdapter() {

    val firstPosition: Int get () = (count / 2).let { it - it % delegate.size }

    override fun getCount() = if (boundless) Int.MAX_VALUE else delegate.size
    
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val realPos = position % delegate.size
        return delegate.getViewHolder(container, realPos).let {
            delegate.bindViewHolder(it, realPos)
            it.itemView.apply { container.addView(this) }
        }
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        delegate.removeViewHolder(position % delegate.size)
    }
}
