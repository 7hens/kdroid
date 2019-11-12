package cn.thens.kdroid.core.vadapter

import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface VAdapter<D> : MutableList<D> {

    fun getItemCount(): Int = size

    fun getItemId(position: Int): Long = position.toLong()

    fun getItemViewType(position: Int): Int = 0

    fun createHolder(viewGroup: ViewGroup, viewType: Int): Holder<D>

    fun bindHolder(holder: Holder<D>, position: Int) {
        holder.inject(this[position], position)
    }

    fun notifyChanged()

    fun refill(elements: Iterable<D>) {
        if (this != elements) {
            clear()
            addAll(elements)
        }
        notifyChanged()
    }

    interface Holder<in D> {
        val view: View

        fun inject(data: D, position: Int)
    }

    companion object {
        fun <D> recycler(@LayoutRes layoutRes: Int, bind: View.(data: D, position: Int) -> Unit): Lazy<VRecyclerAdapter<D>> {
            return lazy { VRecyclerAdapter.create(layoutRes, bind) }
        }

        fun <D> pager(@LayoutRes layoutRes: Int, bind: View.(data: D, position: Int) -> Unit): Lazy<VPagerAdapter<D>> {
            return lazy { VPagerAdapter.create(layoutRes, false, bind) }
        }

        fun pager(fm: FragmentManager, boundless: Boolean = false): Lazy<VFragmentPagerAdapter> {
            return lazy { VFragmentPagerAdapter(fm, boundless) }
        }

        fun <D> list(@LayoutRes layoutRes: Int, bind: View.(data: D, position: Int) -> Unit): Lazy<VListAdapter<D>> {
            return lazy { VListAdapter.create(layoutRes, bind) }
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun <V : View, D> V.toHolder(bind: V.(data: D, position: Int) -> Unit): VAdapter.Holder<D> {
    return object : VAdapter.Holder<D> {
        override val view = this@toHolder
        override fun inject(data: D, position: Int) {
            bind(data, position)
        }
    }
}

