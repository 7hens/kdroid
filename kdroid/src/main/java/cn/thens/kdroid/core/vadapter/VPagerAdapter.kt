package cn.thens.kdroid.core.vadapter

import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import java.lang.ref.WeakReference

/**
 * The Adapter for [ViewPager][android.support.v4.view.PagerAdapter].
 */
abstract class VPagerAdapter<D>(private val boundless: Boolean = false) : PagerAdapter(), VBaseAdapter<D>, MutableList<D> by ArrayList<D>() {

    override val holderList = SparseArray<VAdapter.Holder<D>>()

    override fun getCount(): Int {
        return if (boundless && size > 1) Integer.MAX_VALUE else size
    }

    val startIndex: Int get () = (count / 2).let { it - it % size }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    private fun getSmartSize(size: Int): Int {
        val limit = 4
        var result = size
        while (result < limit) {
            result += size
        }
        return result
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val realPos = position % getSmartSize(size)
        val holder = getHolder(container, realPos)
        val itemView = holder.view
        (itemView.parent as? ViewGroup)?.removeView(itemView)
        container.addView(itemView)
        bindHolder(holder, realPos % size)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        removeHolder(position % getSmartSize(size))
    }

    companion object {
        fun <D> create(@LayoutRes layoutRes: Int, boundless: Boolean = false, bind: View.(data: D, position: Int) -> Unit): VPagerAdapter<D> {
            return object : VPagerAdapter<D>(boundless) {
                override fun createHolder(viewGroup: ViewGroup, viewType: Int): VAdapter.Holder<D> {
                    return viewGroup.inflate(layoutRes).toHolder(bind)
                }
            }
        }
    }
}
