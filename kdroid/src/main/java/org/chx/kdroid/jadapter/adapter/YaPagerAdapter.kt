package org.chx.kdroid.jadapter.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import org.chx.kdroid.jadapter.proxy.ViewHolder

class YaPagerAdapter<D>(val proxy: ViewHolder.Factory<D>, val boundless: Boolean = false) : PagerAdapter() {
    val holders = HashMap<Int, ViewHolder<D>>()

    override fun getCount() = if (boundless) Int.MAX_VALUE else proxy.size

    fun positionOf(index: Int): Int {
        val halfCount = count / 2
        return halfCount - halfCount % proxy.size + index
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return proxy.getView(container, position).apply { container.addView(this) }
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        proxy.removeViewHolder(position)
    }
}
