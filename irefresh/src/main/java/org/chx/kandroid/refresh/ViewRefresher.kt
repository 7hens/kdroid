package cn.zhmj.zikao.kt.base.irefresh


abstract class ViewRefresher<D, V, A>(protected var view: V, delegate: RefreshDelegate) : DelegatedDataRefresher<D>(delegate) {
    protected val adapter: A by lazy {
        val adapter = getAdapter(data)
        bindAdapter(adapter)
        return@lazy adapter
    }

    abstract fun getAdapter(dataList: List<D>): A

    protected abstract fun bindAdapter(adapter: A)

    abstract fun notifyDataSetChanged()

    override fun onDataSetChanged(convertedData: List<D>?) {
        super.onDataSetChanged(convertedData)
        notifyDataSetChanged()
    }
}
