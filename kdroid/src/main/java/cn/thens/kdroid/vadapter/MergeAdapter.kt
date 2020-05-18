package cn.thens.kdroid.vadapter

import android.view.ViewGroup

class MergeAdapter : VAdapter {
    val adapters = mutableListOf<VAdapter>()

    override val itemCount: Int
        get() = adapters.fold(0) { acc, v -> acc + v.itemCount }

    override fun getItemType(position: Int): Int {
        return position
    }

    override fun createHolder(container: ViewGroup, itemType: Int): VAdapter.Holder {
        var position = itemType
        adapters.forEach { adapter ->
            if (position < adapter.itemCount) {
                return adapter.createHolder(container, adapter.getItemType(position))
            }
            position -= adapter.itemCount
        }
        throw IndexOutOfBoundsException("expect $itemType, actual $itemCount")
    }
}