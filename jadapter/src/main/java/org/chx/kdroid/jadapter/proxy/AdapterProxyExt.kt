package org.chx.kdroid.jadapter.proxy

import org.chx.kdroid.jadapter.adapter.YaListAdapter
import org.chx.kdroid.jadapter.adapter.YaPagerAdapter
import org.chx.kdroid.jadapter.adapter.YaRecyclerAdapter


fun <D> ViewHolderProvider<D>.list() = YaListAdapter(this)

fun <D> ViewHolderProvider<D>.pager(boundless: Boolean = false) = YaPagerAdapter(this, boundless)

fun <D> AdapterProxy<D>.recycler() = YaRecyclerAdapter(this)