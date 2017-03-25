package org.chx.kdroid.kadapter.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.chx.kdroid.kadapter.HolderView

class YaRecyclerAdapter<D>(val delegate: HolderView.Factory<D>) : RecyclerView.Adapter<YaRecyclerAdapter.YaViewHolder<D>>() {

    override fun getItemCount() = delegate.size

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): YaViewHolder<D> {
        return YaViewHolder(delegate.createView(container, viewType))
    }

    override fun onBindViewHolder(holder: YaViewHolder<D>, position: Int) {
        delegate.bindView(holder.holder, position)
    }

    class YaViewHolder<in D>(val holder: HolderView<D>) : RecyclerView.ViewHolder(holder)
}
