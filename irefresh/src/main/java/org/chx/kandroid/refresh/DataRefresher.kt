package cn.zhmj.zikao.kt.base.irefresh

import cn.zhmj.zikao.kt.base.rx.EmptyObserver
import cn.zhmj.zikao.kt.base.rx.observeOnMainThread
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import java.util.*

abstract class DataRefresher<D> {
    val data = ArrayList<D>()
    private var pageIndex = DEFAULT_PAGE_INDEX
    private val observerList = ArrayList<ObservableEmitter<in List<D>>>()

    protected abstract fun observeAt(pageIndex: Int): Observable<List<D>>

    fun refresh() {
        pageIndex = DEFAULT_PAGE_INDEX
        data.clear()
        loadMore()
    }

    fun loadMore() {
        observeAt(pageIndex)
                .observeOnMainThread()
                .subscribe(object : EmptyObserver<List<D>>() {
                    override fun onNext(t: List<D>) {
                        onDataSetChanged(t)
                    }

                    override fun onError(e: Throwable?) {
                        super.onError(e)
                        onDataSetChanged(null)
                    }
                })
    }

    fun whenDataSetChanged(): Observable<List<D>> = Observable.create { observerList.add(it) }

    protected open fun onDataSetChanged(convertedData: List<D>?) {
        if (pageIndex == DEFAULT_PAGE_INDEX) data.clear()
        if (convertedData != null && !convertedData.isEmpty()) {
            pageIndex += 1
            data.addAll(convertedData)
        }
        for (observer in observerList) {
            observer.onNext(convertedData)
        }
    }

    companion object {
        private val DEFAULT_PAGE_INDEX = 1
    }
}
