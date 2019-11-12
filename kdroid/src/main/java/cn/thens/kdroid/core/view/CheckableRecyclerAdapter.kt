package cn.thens.kdroid.core.view

abstract class CheckableRecyclerAdapter<E> : FastRecyclerAdapter<E>() {
    private val oldCheckedItems = mutableSetOf<E>()
    private val newCheckedItems = mutableSetOf<E>()

    fun getCheckedItems(): List<E> {
        return oldCheckedItems.toList()
    }

    fun setCheckedItems(checkedItems: Iterable<E>) {
        newCheckedItems.clear()
        newCheckedItems.addAll(checkedItems)
        refill(dataSource)
        oldCheckedItems.clear()
        oldCheckedItems.addAll(checkedItems)
    }
    
    fun removeChecked() {
        val applets = dataSource.filter { it !in oldCheckedItems }
        newCheckedItems.clear()
        refill(applets)
        oldCheckedItems.clear()
    }

    fun checkAll() {
        newCheckedItems.addAll(dataSource)
        refill(dataSource)
        oldCheckedItems.addAll(dataSource)
    }

    fun uncheckAll() {
        newCheckedItems.clear()
        refill(dataSource)
        oldCheckedItems.clear()
    }

    fun isItemChecked(item: E): Boolean {
        return item in oldCheckedItems
    }

    fun checkItem(item: E, isChecked: Boolean) {
        if (isChecked == isItemChecked(item)) return
        if (isChecked) {
            newCheckedItems.add(item)
        } else {
            newCheckedItems.remove(item)
        }
        refill(dataSource)
        if (isChecked) {
            oldCheckedItems.add(item)
        } else {
            oldCheckedItems.remove(item)
        }
    }

    override fun areContentsTheSame(oldItem: E, newItem: E): Boolean {
        val isOldChecked = oldItem in oldCheckedItems
        val isNewChecked = newItem in newCheckedItems
        return isOldChecked == isNewChecked
    }
}