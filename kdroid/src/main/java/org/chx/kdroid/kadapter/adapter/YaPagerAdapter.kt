package org.chx.kdroid.kadapter.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import org.chx.kdroid.kadapter.KAdapter

class YaPagerAdapter<D>(val delegate: KAdapter<D>, val boundless: Boolean = false) : PagerAdapter() {

    val firstPosition: Int get () = (count / 2).let { it - it % delegate.size }

    override fun getCount() = if (boundless) Int.MAX_VALUE else delegate.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val realPos = position % delegate.size
        return delegate.getView(container, realPos).apply {
            delegate.bindView(this, realPos)
            (parent as? ViewGroup)?.removeView(this)
            container.addView(this)
        }
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        delegate.removeView(position % delegate.size)
    }
}
