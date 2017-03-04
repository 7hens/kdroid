package org.chx.kandroid.jadapter.adapter

import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.chx.kandroid.jadapter.ViewHolder
import org.chx.kandroid.jadapter.ViewAdapter

internal class YaPagerAdapter<D>(val dispatcher: ViewAdapter<D>, val boundless:Boolean) : PagerAdapter() {
    val views = SparseArray<View>()

    override fun getCount() = if (boundless) Int.MAX_VALUE else  dispatcher.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val realPos = position % dispatcher.size
        val data = dispatcher[realPos]
        val viewHolder: ViewHolder<D>
        val itemView: View
        if (views[realPos] == null) {
            val layoutRes = dispatcher.getLayoutRes(dispatcher.getViewType(realPos))
            itemView = LayoutInflater.from(container.context).inflate(layoutRes, null)
            viewHolder = dispatcher.getViewHolder(itemView)
            itemView.tag = viewHolder
            views.setValueAt(realPos, itemView)
        } else {
            itemView = views[realPos]
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
        val realPos = position % dispatcher.size
        val itemView = views[realPos]
        if (itemView != null && itemView.visibility != View.VISIBLE) container.removeView(itemView)
    }
}
