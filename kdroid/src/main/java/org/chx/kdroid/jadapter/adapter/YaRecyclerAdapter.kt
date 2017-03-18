package org.chx.kdroid.jadapter.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.chx.kdroid.jadapter.proxy.ViewHolder
import org.chx.kdroid.jadapter.proxy.AdapterProxy

class YaRecyclerAdapter<D>(val proxy: AdapterProxy<D>) : RecyclerView.Adapter<YaRecyclerAdapter.YaViewHolder<D>>() {

    override fun getItemCount() = proxy.size

    override fun getItemViewType(position: Int) = proxy.getViewType(position)

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): YaViewHolder<D>
            = YaViewHolder(proxy.createViewHolderWithViewType(container, viewType))

    override fun onBindViewHolder(holder: YaViewHolder<D>, position: Int) {
        proxy.bindViewHolder(holder.holder, position)
    }

    class YaViewHolder<in D>(val holder: ViewHolder<D>) : RecyclerView.ViewHolder(holder.itemView)
}
