package org.chx.kdroid.kadapter

import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import org.chx.kdroid.kandy.view.removeFromParent
import java.lang.ref.WeakReference

abstract class KAdapter<D>(dataList: List<D>) : HolderView.Factory<D>(dataList) {
    private val viewList = HashMap<Int, WeakReference<HolderView<D>>>()

    fun getView(container: ViewGroup, position: Int): HolderView<D> {
        return viewList[position]?.get() ?: createView(container, position).apply { viewList[position] = WeakReference(this) }
    }

    fun getView(position: Int) = viewList[position]

    fun removeView(position: Int): HolderView<D>? {
        return viewList[position]?.get()?.apply {
            if (visibility != View.VISIBLE) {
                removeFromParent()
                viewList.remove(position)
            }
        }
    }

    companion object {
        fun <D> singleLayout(dataList: List<D>, @LayoutRes layoutRes: Int, convertFunc: HolderView<D>.(D, Int) -> Unit): KAdapter<D> =
                object : KAdapter<D>(dataList) {
                    override fun createView(container: ViewGroup, position: Int) =
                            object : HolderView<D>(container, layoutRes) {
                                override fun convert(data: D, position: Int) = convertFunc(data, position)
                            }
                }
    }
}