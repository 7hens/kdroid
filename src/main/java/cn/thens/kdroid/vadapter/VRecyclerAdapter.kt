package cn.thens.kdroid.vadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * the Adapter for [RecyclerView][android.support.v7.widget.RecyclerView]
 */
abstract class VRecyclerAdapter : RecyclerView.Adapter<VRecyclerAdapter.Holder>() {
    abstract val adapter: VAdapter

    override fun getItemCount(): Int = adapter.itemCount

    override fun getItemViewType(position: Int): Int = adapter.getItemType(position)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        return Holder(adapter.createHolder(viewGroup, viewType))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        adapter.bindHolder(holder.holder, position)
    }

    open class Holder(val holder: VAdapter.Holder) : RecyclerView.ViewHolder(holder.view)
}