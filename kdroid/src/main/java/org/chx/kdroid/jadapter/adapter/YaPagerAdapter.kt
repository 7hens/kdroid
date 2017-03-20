package org.chx.kdroid.jadapter.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import org.chx.kdroid.jadapter.delegate.AdapterDelegate
import org.chx.kdroid.jadapter.delegate.ViewHolder

class YaPagerAdapter<D>(val delegate: AdapterDelegate<D>, val boundless: Boolean = false) : PagerAdapter() {
    val holders = HashMap<Int, ViewHolder<D>>()

    override fun getCount() = if (boundless) Int.MAX_VALUE else delegate.size

    fun positionOf(index: Int): Int {
        val halfCount = count / 2
        return halfCount - halfCount % delegate.size + index
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return delegate.getViewHolder(container, position % delegate.size).itemView.apply { container.addView(this) }
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        delegate.removeViewHolder(position % delegate.size)
    }
}
