package cn.thens.kdroid.core.vadapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * the Adapter for [RecyclerView][android.support.v7.widget.RecyclerView]
 */
abstract class VRecyclerAdapter<D> : RecyclerView.Adapter<VRecyclerAdapter.Holder<D>>(), VAdapter<D>, MutableList<D> by ArrayList<D>() {
    private var oldList = Collections.synchronizedList(ArrayList<D>())

    private val differ: DiffCallback by lazy { DiffCallback() }

    open fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(oldItemPosition, newItemPosition)
    }

    open fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]?.equals(this[newItemPosition]) ?: false
    }

    open fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return this[newItemPosition]
    }

    override fun notifyChanged() {
        differ.notifyChanged()
    }

    override fun getItemCount(): Int = oldList.size

    open fun getItemViewType(list: MutableList<D>, position: Int): Int = 0

    override fun getItemViewType(position: Int): Int = getItemViewType(oldList, position)

    override fun bindHolder(holder: VAdapter.Holder<D>, position: Int) {
        holder.inject(oldList[position], position)
    }

    override fun getItemId(position: Int) = super<VAdapter>.getItemId(position)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder<D> {
        return Holder(createHolder(viewGroup, viewType))
    }

    override fun onBindViewHolder(holder: Holder<D>, position: Int) {
        bindHolder(holder.holder, position)
    }

    open class Holder<in D>(val holder: VAdapter.Holder<D>) : RecyclerView.ViewHolder(holder.view)

    inner class DiffCallback : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return this@VRecyclerAdapter.areItemsTheSame(oldItemPosition, newItemPosition)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return this@VRecyclerAdapter.areContentsTheSame(oldItemPosition, newItemPosition)
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return this@VRecyclerAdapter.getChangePayload(oldItemPosition, newItemPosition)
        }

        fun notifyChanged() {
            val diff = DiffUtil.calculateDiff(differ)
            oldList.clear()
            oldList.addAll(this@VRecyclerAdapter)
            diff.dispatchUpdatesTo(this@VRecyclerAdapter)
        }
    }

    companion object {
        fun <D> create(@LayoutRes layoutRes: Int, bind: View.(data: D, position: Int) -> Unit): VRecyclerAdapter<D> {
            return object : VRecyclerAdapter<D>() {
                override fun createHolder(viewGroup: ViewGroup, viewType: Int): VAdapter.Holder<D> {
                    return viewGroup.inflate(layoutRes).toHolder(bind)
                }
            }
        }
    }
}