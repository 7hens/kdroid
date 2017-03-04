package cn.zhmj.zikao.kt.base.irefresh.refresher

import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import cn.zhmj.zikao.kt.base.irefresh.RefreshDelegate
import cn.zhmj.zikao.kt.base.irefresh.ViewRefresher

abstract class ExpandableListViewRefresher<D>(view: ExpandableListView, delegate: RefreshDelegate)
    : ViewRefresher<D, ExpandableListView, BaseExpandableListAdapter>(view, delegate) {

    override fun bindAdapter(adapter: BaseExpandableListAdapter) {
        view.setAdapter(adapter)
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }
}