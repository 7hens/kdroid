package org.chx.kdroid.jadapter.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import cn.zhmj.zikao.kt.base.jadapter.ViewHolder
import org.chx.kdroid.jadapter.proxy.ViewHolderProvider

class YaListAdapter<D>(val proxy: ViewHolderProvider<D>) : BaseAdapter()  {
    override fun getCount() = proxy.size

    override fun getItem(position: Int) = proxy[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val viewHolder: ViewHolder<D>
        val data = proxy[position]
        if (itemView == null) {
            viewHolder = proxy.getViewHolder(parent, position)
            itemView = viewHolder.itemView
            itemView.tag = viewHolder
        } else {
            @Suppress("UNCHECKED_CAST")
            viewHolder = itemView.tag as ViewHolder<D>
        }
        viewHolder.position = position
        viewHolder.convert(data)
        return itemView
    }
}
