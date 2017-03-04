package cn.zhmj.zikao.kt.base.irefresh

import cn.zhmj.zikao.kt.base.rx.EmptyObserver


abstract class DelegatedDataRefresher<D>(protected var delegate: RefreshDelegate) : DataRefresher<D>() {
    init {
        delegate.whenLoad().subscribe(EmptyObserver.with {
            when (it) {
                RefreshDelegate.REFRESH -> refresh(true)
                RefreshDelegate.LOAD_MORE -> loadMore(true)
            }
        })
    }

    fun endLoadingAnimation() {
        delegate.isLoadingMore = false
        delegate.isRefreshing = false
    }

    fun refresh(showsAnimation: Boolean) {
        super.refresh()
        delegate.isRefreshing = showsAnimation
    }

    fun loadMore(showsAnimation: Boolean) {
        super.loadMore()
        delegate.isLoadingMore = showsAnimation
    }

    override fun onDataSetChanged(convertedData: List<D>?) {
        super.onDataSetChanged(convertedData)
        endLoadingAnimation()
    }
}
