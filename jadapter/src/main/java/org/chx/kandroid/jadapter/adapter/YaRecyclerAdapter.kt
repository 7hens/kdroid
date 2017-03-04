package org.chx.kandroid.jadapter.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.chx.kandroid.jadapter.ViewHolder
import org.chx.kandroid.jadapter.ViewAdapter

internal class YaRecyclerAdapter<D>(val dispatcher: ViewAdapter<D>) : RecyclerView.Adapter<YaRecyclerAdapter.YaViewHolder<D>>() {

    override fun getItemCount() = dispatcher.size

    override fun getItemViewType(position: Int) = dispatcher.getViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YaViewHolder<D> {
        val layoutResId = dispatcher.getLayoutRes(viewType)
        val itemView = LayoutInflater.from(parent.context).inflate(layoutResId, null, false)
        val viewHolder = dispatcher.getViewHolder(itemView)
        return YaViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: YaViewHolder<D>, position: Int) {
        holder.holder.convert(dispatcher[position])
    }

    class YaViewHolder<in D>(val holder: ViewHolder<D>) : RecyclerView.ViewHolder(holder.itemView)
}
