package cn.thens.kdroid.vadapter

import android.view.View
import android.view.ViewGroup

interface VAdapter {
    val itemCount: Int

    fun getItemType(position: Int): Int = 0

    fun createHolder(container: ViewGroup, itemType: Int): Holder

    fun bindHolder(holder: Holder, position: Int) {
        holder.bind(this, position)
    }

    interface Holder {
        val view: View

        fun bind(adapter: VAdapter, position: Int)
    }
}

