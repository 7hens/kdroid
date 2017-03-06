package org.chx.kandroid.jadapter.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.chx.kandroid.jadapter.ViewHolder
import org.chx.kandroid.jadapter.proxy.AdapterProxy

class YaRecyclerAdapter<D>(val proxy: AdapterProxy<D>) : RecyclerView.Adapter<YaRecyclerAdapter.YaViewHolder<D>>() {

    override fun getItemCount() = proxy.size

    override fun getItemViewType(position: Int) = proxy.getViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YaViewHolder<D> {
        val viewHolder = proxy.getViewHolderWithViewType(parent, viewType)
        return YaViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: YaViewHolder<D>, position: Int) {
        holder.holder.convert(proxy[position])
    }

    class YaViewHolder<in D>(val holder: ViewHolder<D>) : RecyclerView.ViewHolder(holder.itemView)
}
