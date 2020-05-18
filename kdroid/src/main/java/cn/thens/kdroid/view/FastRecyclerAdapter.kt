package cn.thens.kdroid.view

import androidx.recyclerview.widget.DiffUtil

abstract class FastRecyclerAdapter<E> : SimpleRecyclerAdapter<E>() {
    private var oldDataSource = listOf<E>()

    override val dataSource get() = oldDataSource

    open fun areItemsTheSame(oldItem: E, newItem: E): Boolean {
        return oldItem == newItem
    }

    open fun areContentsTheSame(oldItem: E, newItem: E): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

    fun refill(newData: Iterable<E>) {
        val newDataSource = newData.toList()
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldDataSource.size

            override fun getNewListSize(): Int = newDataSource.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areItemsTheSame(oldDataSource[oldItemPosition], newDataSource[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areContentsTheSame(oldDataSource[oldItemPosition], newDataSource[newItemPosition])
            }
        })
        oldDataSource = newDataSource
        diff.dispatchUpdatesTo(this)
    }
}