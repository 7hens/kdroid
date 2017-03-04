package org.chx.kandroid.jadapter

import android.support.annotation.LayoutRes
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ListAdapter
import org.chx.kandroid.jadapter.ViewHolder
import org.chx.kandroid.jadapter.adapter.YaListAdapter
import org.chx.kandroid.jadapter.adapter.YaPagerAdapter
import org.chx.kandroid.jadapter.adapter.YaRecyclerAdapter

abstract class ViewAdapter<D>(dataList: List<D>) : List<D> by dataList {
    open fun getViewType(position: Int): Int = 0

    abstract fun getLayoutRes(viewType: Int): Int

    abstract fun getViewHolder(itemView: View): ViewHolder<D>

    fun list(): ListAdapter = YaListAdapter(this)

    fun recycler(): RecyclerView.Adapter<*> = YaRecyclerAdapter(this)

    fun pager(boundless: Boolean = false): PagerAdapter = YaPagerAdapter(this, boundless)

    companion object {
        fun <D> single(dataList: List<D>, @LayoutRes layoutRes: Int, holderFunc: (View) -> ViewHolder<D>): ViewAdapter<D> {
            return object : ViewAdapter<D>(dataList) {
                override fun getLayoutRes(viewType: Int) = layoutRes

                override fun getViewHolder(itemView: View): ViewHolder<D> = holderFunc(itemView)
            }
        }
    }
}
