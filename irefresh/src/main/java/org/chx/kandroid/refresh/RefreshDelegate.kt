package cn.zhmj.zikao.kt.base.irefresh

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import java.util.*


abstract class RefreshDelegate (val mode:Int = BOTH){
    protected var state = NONE
    protected var observerList: MutableList<ObservableEmitter<in Int>> = ArrayList()

    open var isRefreshing: Boolean
        get() = state and REFRESH != 0
        set(flag) {
            state = if (flag) state or REFRESH else state and REFRESH.inv()
        }

    open var isLoadingMore: Boolean
        get() = state and LOAD_MORE != 0
        set(flag) {
            state = if (flag) state or LOAD_MORE else state and LOAD_MORE.inv()
        }

    open val isLoadMoreEnabled: Boolean
        get() = mode and LOAD_MORE != 0

    open val isRefreshEnabled: Boolean
        get() = mode and REFRESH != 0

    fun whenLoad(): Observable<Int> = Observable.create { observerList.add(it) }

    protected fun onRefresh() {
        subscribeNext(REFRESH)
    }

    protected fun onLoadMore() {
        subscribeNext(LOAD_MORE)
    }

    private fun subscribeNext(state: Int) {
        for (observer in observerList) {
            observer.onNext(state)
        }
    }

    companion object {
        val NONE = 0
        val REFRESH = 1
        val LOAD_MORE = 1 shl 1
        val BOTH = REFRESH or LOAD_MORE
    }
}
