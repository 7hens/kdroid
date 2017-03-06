package org.chx.kandroid.jadapter.proxy

import android.view.ViewGroup
import org.chx.kandroid.jadapter.ViewHolder

abstract class ViewHolderProvider<D>(dataList: List<D>) : List<D> by dataList {
    abstract fun getViewHolder (parent: ViewGroup, position: Int): ViewHolder<D>
}
