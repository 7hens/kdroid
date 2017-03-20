package org.chx.kdroid.jadapter.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.chx.kdroid.jadapter.delegate.ViewHolder

class YaRecyclerAdapter<D>(val delegate: ViewHolder.Factory<D>) : RecyclerView.Adapter<YaRecyclerAdapter.YaViewHolder<D>>() {

    override fun getItemCount() = delegate.size

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): YaViewHolder<D>
            = YaViewHolder(delegate.createViewHolder(container, viewType))

    override fun onBindViewHolder(holder: YaViewHolder<D>, position: Int) {
        delegate.bindViewHolder(holder.holder, position)
    }

    class YaViewHolder<in D>(val holder: ViewHolder<D>) : RecyclerView.ViewHolder(holder.itemView)
}
