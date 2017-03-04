package cn.zhmj.zikao.kt.base.irefresh.refresher

import android.widget.AbsListView
import android.widget.BaseAdapter
import cn.zhmj.zikao.kt.base.irefresh.RefreshDelegate
import cn.zhmj.zikao.kt.base.irefresh.ViewRefresher


abstract class ListViewRefresher<D>(view: AbsListView, delegate: RefreshDelegate)
    : ViewRefresher<D, AbsListView, BaseAdapter>(view, delegate) {
    override fun bindAdapter(adapter: BaseAdapter) {
        view.adapter = adapter
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }
}
