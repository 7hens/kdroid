package cn.thens.kdroid.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class SimpleRecyclerAdapter<E> : RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder<E>>() {
    protected abstract val dataSource: List<E>

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: ViewHolder<E>, position: Int) {
        holder.bindView(dataSource[position], position)
    }

    abstract class ViewHolder<E>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindView(data: E, position: Int)

        companion object {
            @JvmStatic
            inline fun <E> create(view: View, crossinline fn: View.(E, Int) -> Unit): ViewHolder<E> {
                return object : ViewHolder<E>(view) {
                    override fun bindView(data: E, position: Int) {
                        itemView.fn(data, position)
                    }
                }
            }

            @JvmStatic
            inline fun <E> create(parent: ViewGroup, layoutRes: Int, crossinline fn: View.(E, Int) -> Unit): ViewHolder<E> {
                return create(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false), fn)
            }
        }
    }
}