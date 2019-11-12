package cn.thens.kdroid.core.vadapter

import androidx.annotation.LayoutRes
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.lang.ref.WeakReference

/**
 * The Adapter for [ListView][android.widget.ListView], [GridView][android.widget.GridView], [Spinner][android.widget.Spinner] and etc.
 */
abstract class VListAdapter<D> : BaseAdapter(), VBaseAdapter<D>, MutableList<D> by ArrayList() {

    override val holderList = SparseArray<VAdapter.Holder<D>>()

    override fun isEmpty(): Boolean = size == 0

    override fun getItemViewType(position: Int): Int {
        return super<VBaseAdapter>.getItemViewType(position)
    }

    override fun notifyChanged() {
        notifyDataSetChanged()
    }

    override fun getCount(): Int = getItemCount()

    override fun getItem(position: Int): D {
        return this[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder = getHolder(parent, position)
        bindHolder(holder, position)
        return holder.view
    }

    companion object {
        fun <D> create(@LayoutRes layoutRes: Int, bind: View.(data: D, position: Int) -> Unit): VListAdapter<D> {
            return object : VListAdapter<D>() {
                override fun createHolder(viewGroup: ViewGroup, viewType: Int): VAdapter.Holder<D> {
                    return viewGroup.inflate(layoutRes).toHolder(bind)
                }
            }
        }
    }
}