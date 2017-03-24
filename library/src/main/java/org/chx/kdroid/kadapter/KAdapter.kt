package org.chx.kdroid.kadapter

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.chx.kdroid.kadapter.HolderView

abstract class KAdapter<D>(dataList: List<D>) : HolderView.Factory<D>(dataList) {
    private val viewList = HashMap<Int, HolderView<D>>()

    fun getView(container: ViewGroup, position: Int): HolderView<D> {
        return viewList[position] ?: createView(container, position).apply { viewList[position] = this }
    }

    fun getView(position: Int) = viewList[position]

    fun removeView(position: Int): HolderView<D>? {
        return viewList[position]?.apply {
            if (visibility != View.VISIBLE) {
                (parent as? ViewGroup)?.removeView(this)
                viewList.remove(position)
            }
        }
    }

    companion object {
        fun <D> singleLayout(dataList: List<D>, @LayoutRes layoutRes: Int, holderFunc: (View) -> HolderView<D>): KAdapter<D> {
            return object : KAdapter<D>(dataList) {
                override fun createView(container: ViewGroup, position: Int): HolderView<D> {
                    return holderFunc(LayoutInflater.from(container.context).inflate(layoutRes, container, false))
                }
            }
        }
    }
}