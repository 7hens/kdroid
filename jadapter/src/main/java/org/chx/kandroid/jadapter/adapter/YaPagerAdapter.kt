package org.chx.kandroid.jadapter.adapter

import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import org.chx.kandroid.jadapter.ViewHolder
import org.chx.kandroid.jadapter.proxy.ViewHolderProvider

class YaPagerAdapter<D>(val proxy: ViewHolderProvider<D>, val boundless: Boolean = false) : PagerAdapter() {
    val views = SparseArray<View>()

    override fun getCount() = if (boundless) Int.MAX_VALUE else proxy.size

    fun positionOf(index: Int): Int {
        val halfCount = count / 2
        return halfCount - halfCount % proxy.size + index
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val realPos = position % proxy.size
        val data = proxy[realPos]
        var itemView = views[realPos]
        val viewHolder: ViewHolder<D>
        if (itemView == null) {
            viewHolder = proxy.getViewHolder(container, position)
            itemView = viewHolder.itemView
            itemView.tag = viewHolder
            views.setValueAt(realPos, itemView)
        } else {
            @Suppress("UNCHECKED_CAST")
            viewHolder = itemView.tag as ViewHolder<D>
            (itemView.parent as? ViewGroup)?.removeView(itemView)
        }
        container.addView(itemView)
        viewHolder.position = realPos
        viewHolder.convert(data)
        return itemView
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val realPos = position % proxy.size
        val itemView = views[realPos]
        if (itemView != null && itemView.visibility != View.VISIBLE) container.removeView(itemView)
    }
}
