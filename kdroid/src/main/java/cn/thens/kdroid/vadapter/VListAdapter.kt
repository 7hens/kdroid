package cn.thens.kdroid.vadapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.LayoutRes

/**
 * The Adapter for [ListView][android.widget.ListView], [GridView][android.widget.GridView], [Spinner][android.widget.Spinner] and etc.
 */
open class VListAdapter(private val adapter: VAdapterHelper) : BaseAdapter() {
    override fun getCount(): Int = adapter.itemCount

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder = adapter.createHolder(parent, position)
        adapter.bindHolder(holder, position)
        return holder.view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}