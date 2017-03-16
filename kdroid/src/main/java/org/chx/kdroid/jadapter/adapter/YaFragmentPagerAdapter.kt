package org.chx.kdroid.jadapter.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class YaFragmentPagerAdapter(fm: FragmentManager, private val fragClassList:List<Class<out Fragment>>, val boundless:Boolean = false)
    : FragmentPagerAdapter(fm) {

    private val fragmentList = HashMap<Int, Fragment>()
    
    override fun getCount() = if (boundless) Int.MAX_VALUE else fragClassList.size

    override fun getItem(position: Int): Fragment {
        val realPos = position % fragClassList.size
        var fragment = fragmentList[realPos]
        if (fragment == null) {
            fragment = fragClassList[realPos].newInstance()
            fragmentList[realPos] = fragment
            return fragment
        }
        return fragment
    }

    fun positionOf(index: Int): Int {
        val halfCount = count / 2
        return halfCount - halfCount % fragClassList.size + index
    }
}