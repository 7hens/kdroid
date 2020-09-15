package cn.thens.kdroid.vadapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * The Adapter for [ViewPager][android.support.v4.view.PagerAdapter].
 */
abstract class VPagerAdapter : PagerAdapter() {
    abstract val adapter: VAdapterHelper

    override fun getCount(): Int = adapter.itemCount

    val startIndex: Int get() = adapter.startIndex

    override fun notifyDataSetChanged() {
        adapter.notifyItemsChanged()
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val holder = adapter.createHolder(container, position)
        val itemView = holder.view
        container.addView(itemView)
        adapter.bindHolder(holder, position)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        adapter.removeHolder(position)
    }
}
