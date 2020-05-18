package cn.thens.kdroid.vadapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

open class VAdapterHelper(private val adapter: VAdapter) : VAdapter {
    private val holders: SparseArray<VAdapter.Holder> = SparseArray()

    override val itemCount: Int get() = adapter.itemCount

    val startIndex: Int get() = (itemCount / 2).let { it - it % adapter.itemCount }

    override fun getItemType(position: Int): Int = adapter.getItemType(position % adapter.itemCount)

    override fun createHolder(container: ViewGroup, itemType: Int): VAdapter.Holder {
        val holderPosition = itemType % holderSize
        var holder = holders[holderPosition]
        if (holder == null) {
            holder = adapter.createHolder(container, getItemType(itemType))
            holders.put(holderPosition, holder)
        }
        freeView(holder.view)
        return holder
    }

    fun removeHolder(position: Int): VAdapter.Holder? {
        val holderPosition = position % holderSize
        val holder = holders[holderPosition]
        freeView(holder.view)
        holders.remove(holderPosition)
        return holder
    }

    fun notifyItemsChanged() {
        for (i in 0 until holders.size()) {
            val position = holders.keyAt(i)
            holders[position].bind(this, position % adapter.itemCount)
        }
    }

    private fun freeView(view: View) {
        (view.parent as? ViewGroup)?.removeView(view)
    }

    private val holderSize: Int
        get() {
            val size = adapter.itemCount
            val limit = 3
            var result = size
            while (result < limit) {
                result += size
            }
            return result
        }

    fun boundless(): VAdapterHelper {
        return if (this is Boundless) this else Boundless(adapter)
    }

    private class Boundless(adapter: VAdapter) : VAdapterHelper(adapter) {
        override val itemCount: Int
            get() {
                val itemCount = super.itemCount
                return if (itemCount > 1) Integer.MAX_VALUE else itemCount
            }
    }
}