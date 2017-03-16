package org.chx.kdroid.irefresh

abstract class ViewRefresher<D, V, A>(protected var view: V, delegate: RefreshDelegate) : DelegatedDataRefresher<D>(delegate) {
    protected val adapter by lazy { getAdapter(data).apply { bindAdapter(this) } }

    abstract fun getAdapter(dataList: List<D>): A

    protected abstract fun bindAdapter(adapter: A)

    abstract fun notifyDataSetChanged()

    override fun onDataSetChanged(convertedData: List<D>?) {
        super.onDataSetChanged(convertedData)
        notifyDataSetChanged()
    }
}
