package org.chx.kdroid.irefresh.refresher

import android.widget.AbsListView
import android.widget.BaseAdapter
import org.chx.kdroid.irefresh.RefreshDelegate
import org.chx.kdroid.irefresh.ViewRefresher

abstract class ListViewRefresher<D>(view: AbsListView, delegate: RefreshDelegate)
    : ViewRefresher<D, AbsListView, BaseAdapter>(view, delegate) {
    override fun bindAdapter(adapter: BaseAdapter) {
        view.adapter = adapter
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }
}
