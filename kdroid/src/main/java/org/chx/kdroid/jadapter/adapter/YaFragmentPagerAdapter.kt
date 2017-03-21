package org.chx.kdroid.jadapter.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class YaFragmentPagerAdapter(fm: FragmentManager, private val fragClassList: List<Class<out Fragment>>, val boundless: Boolean = false)
    : FragmentPagerAdapter(fm) {

    private val fragmentList = HashMap<Int, Fragment>()

    val firstPosition: Int get () = (count / 2).let { it - it % fragClassList.size }

    override fun getCount() = if (boundless) Int.MAX_VALUE else fragClassList.size

    override fun getItem(position: Int): Fragment {
        val realPos = position % fragClassList.size
        return fragmentList[realPos] ?: fragClassList[realPos].newInstance().apply { fragmentList[realPos] = this }
    }
}