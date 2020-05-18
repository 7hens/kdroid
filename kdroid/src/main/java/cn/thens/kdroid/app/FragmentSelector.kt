package cn.thens.kdroid.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cn.thens.kdroid.util.Logdog

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class FragmentSelector(
        private val fragmentManager: FragmentManager,
        private val containerId: Int) {

    abstract fun getFragmentClass(position: Int): Class<out Fragment>

    abstract fun getFragmentSize(): Int

    open fun shouldCacheFragment(position: Int): Boolean = true

    var currentPosition: Int = DEFAULT_POSITION
        private set(value) {
            field = value
        }

    private fun isIndexOutOfBounds(index: Int): Boolean {
        return index !in 0 until getFragmentSize()
    }

    fun select(position: Int) {
        if (isIndexOutOfBounds(position) || fragmentManager.isDestroyed) return
        if (currentPosition == position) {
            val fragment = getFragment(position)
            if (fragment != null && fragment.isAdded) {
                fragment.setVisible(true)
                return
            }
        }
        val transaction = fragmentManager.beginTransaction()
        val nextFragment = transaction.show(position)
        val prevFragment = transaction.hide(currentPosition)
        transaction.commitNowAllowingStateLoss()
        currentPosition = position
        nextFragment?.setVisible(true)
        prevFragment?.setVisible(false)
    }

    fun getFragment(position: Int): Fragment? {
        if (isIndexOutOfBounds(position)) return null
        return fragmentManager.findFragmentByTag(getFragmentTag(position))
    }

    fun resume() {
        select(if (currentPosition == DEFAULT_POSITION) 0 else currentPosition)
    }

    private fun getFragmentTag(position: Int): String {
        val fragClassName = getFragmentClass(position).canonicalName
        return "FragmentSelector.$fragClassName._$containerId._$position"
    }

    private fun FragmentTransaction.show(position: Int): Fragment? {
        return try {
            val tag = getFragmentTag(position)
            val fragment = fragmentManager.findFragmentByTag(tag)
                    ?: getFragmentClass(position).newInstance()
            if (fragment.isAdded) {
                show(fragment)
            } else {
                add(containerId, fragment, tag)
            }
            fragment
        } catch (e: Throwable) {
            Logdog.error(e)
            null
        }
    }

    private fun FragmentTransaction.hide(position: Int): Fragment? {
        val fragment = getFragment(position) ?: return null
        if (shouldCacheFragment(position)) hide(fragment) else remove(fragment)
        return fragment
    }

    private fun Fragment.setVisible(isVisible: Boolean) {
        setMenuVisibility(isVisible)
        userVisibleHint = isVisible
    }

    companion object {
        private const val DEFAULT_POSITION = -1

        fun create(fragmentManager: FragmentManager, containerId: Int, fragClasses: List<Class<out Fragment>>): FragmentSelector {
            return object : FragmentSelector(fragmentManager, containerId) {
                override fun getFragmentSize(): Int {
                    return fragClasses.size
                }

                override fun getFragmentClass(position: Int): Class<out Fragment> {
                    return fragClasses[position]
                }
            }
        }
    }
}