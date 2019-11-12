package cn.thens.kdroid.core.vadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * The Adapter for [ViewPager][android.support.v4.view.PagerAdapter].
 */
open class VFragmentPagerAdapter(fm: FragmentManager, private val boundless: Boolean = false)
    : FragmentPagerAdapter(fm), MutableList<Fragment> by ArrayList() {

    val startIndex: Int get () = (count / 2).let { it - it % size }

    override fun getCount() = if (boundless) Int.MAX_VALUE else size

    override fun getItem(position: Int): Fragment {
        val realPos = position % size
        return this[realPos]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun notifyChanged() {
        notifyDataSetChanged()
    }

    fun refill(elements: Iterable<Fragment>) {
        if (this != elements) {
            clear()
            addAll(elements)
        }
        notifyChanged()
    }
}