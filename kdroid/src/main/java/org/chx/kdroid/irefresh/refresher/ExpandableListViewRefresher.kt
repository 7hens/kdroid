package org.chx.kdroid.irefresh.refresher

import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import org.chx.kdroid.irefresh.RefreshDelegate
import org.chx.kdroid.irefresh.ViewRefresher

abstract class ExpandableListViewRefresher<D>(view: ExpandableListView, delegate: RefreshDelegate)
    : ViewRefresher<D, ExpandableListView, BaseExpandableListAdapter>(view, delegate) {

    override fun bindAdapter(adapter: BaseExpandableListAdapter) {
        view.setAdapter(adapter)
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }
}