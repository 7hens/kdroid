package cn.thens.kdroid.core.vadapter

import android.util.SparseArray
import android.view.ViewGroup
import java.lang.ref.WeakReference

interface VBaseAdapter<D> : VAdapter<D> {
    val holderList: SparseArray<VAdapter.Holder<D>>

    private fun getHolder(position: Int): VAdapter.Holder<D>? {
        return holderList.get(position)
    }

    fun getHolder(container: ViewGroup, position: Int): VAdapter.Holder<D> {
        var holder = getHolder(position)
        if (holder == null) {
            holder = createHolder(container, getItemViewType(position))
            holderList.put(position, holder)
        }
        return holder
    }

    fun removeHolder(position: Int): VAdapter.Holder<D>? {
        val holder = getHolder(position)
        if (holder != null) {
            val view = holder.view
            (view.parent as? ViewGroup)?.removeView(view)
            holderList.delete(position)
        }
        return holder
    }

    override fun notifyChanged() {
        for (i in 0 until holderList.size()) {
            val position = holderList.keyAt(i)
            if (position < 0 || position >= size) continue
            val holder = holderList[position] ?: continue
            bindHolder(holder, position)
        }
    }
}
