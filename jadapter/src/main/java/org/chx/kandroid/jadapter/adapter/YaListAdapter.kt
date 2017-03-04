package org.chx.kandroid.jadapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.chx.kandroid.jadapter.ViewHolder
import org.chx.kandroid.jadapter.ViewAdapter

internal class YaListAdapter<D>(val dispatcher: ViewAdapter<D>) : BaseAdapter()  {
    override fun getCount() = dispatcher.size

    override fun getItem(position: Int) = dispatcher[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val viewHolder: ViewHolder<D>
        val data = dispatcher[position]
        if (itemView == null) {
            val layoutRes = dispatcher.getLayoutRes(dispatcher.getViewType(position))
            itemView = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
            viewHolder = dispatcher.getViewHolder(itemView)
            itemView!!.tag = viewHolder
        } else {
            @Suppress("UNCHECKED_CAST")
            viewHolder = itemView.tag as ViewHolder<D>
        }
        viewHolder.position = position
        viewHolder.convert(data)
        return itemView
    }
}
