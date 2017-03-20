package org.chx.kdroid.jadapter.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.chx.kdroid.jadapter.delegate.AdapterDelegate

class YaListAdapter<D>(val delegate: AdapterDelegate<D>) : BaseAdapter() {
    override fun getItem(position: Int) = delegate[position]

    override fun getCount() = delegate.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, container: ViewGroup)
            = delegate.getViewHolder(container, position).itemView
}
