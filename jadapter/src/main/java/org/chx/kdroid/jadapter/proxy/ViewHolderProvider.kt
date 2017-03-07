package org.chx.kdroid.jadapter.proxy

import android.view.ViewGroup
import cn.zhmj.zikao.kt.base.jadapter.ViewHolder

abstract class ViewHolderProvider<D>(dataList: List<D>) : List<D> by dataList {
    abstract fun getViewHolder (parent: ViewGroup, position: Int): ViewHolder<D>
}
