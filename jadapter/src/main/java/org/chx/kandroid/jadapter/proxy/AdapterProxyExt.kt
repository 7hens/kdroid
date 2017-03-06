package org.chx.kandroid.jadapter.proxy

import org.chx.kandroid.jadapter.adapter.YaListAdapter
import org.chx.kandroid.jadapter.adapter.YaPagerAdapter
import org.chx.kandroid.jadapter.adapter.YaRecyclerAdapter


fun <D> ViewHolderProvider<D>.list() = YaListAdapter(this)

fun <D> ViewHolderProvider<D>.pager(boundless: Boolean = false) = YaPagerAdapter(this, boundless)

fun <D> AdapterProxy<D>.recycler() = YaRecyclerAdapter(this)