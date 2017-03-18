package org.chx.kdroid.jadapter.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.chx.kdroid.jadapter.proxy.ViewHolder

class YaListAdapter<D>(val proxy: ViewHolder.Factory<D>) : BaseAdapter() {
    override fun getCount() = proxy.size

    override fun getItem(position: Int) = proxy[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, container: ViewGroup) = proxy.getView(container, position)
}
