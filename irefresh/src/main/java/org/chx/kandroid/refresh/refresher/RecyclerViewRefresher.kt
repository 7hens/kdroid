package cn.zhmj.zikao.kt.base.irefresh.refresher

import android.support.v7.widget.RecyclerView
import cn.zhmj.zikao.kt.base.irefresh.RefreshDelegate
import cn.zhmj.zikao.kt.base.irefresh.ViewRefresher


abstract class RecyclerViewRefresher<D>(view: RecyclerView, delegate: RefreshDelegate)
    : ViewRefresher<D, RecyclerView, RecyclerView.Adapter<*>>(view, delegate) {

    override protected fun bindAdapter(adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }
}