package org.chx.kdroid.jadapter.adapter

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import org.chx.kdroid.jadapter.proxy.AdapterProxy
import org.chx.kdroid.jadapter.proxy.ViewHolder
import kotlin.reflect.KClass

fun <D> ViewHolder.Factory<D>.listAdapter() = YaListAdapter(this)

fun <D> ViewHolder.Factory<D>.pagerAdapter(boundless: Boolean = false) = YaPagerAdapter(this, boundless)

fun <D> AdapterProxy<D>.recyclerAdapter() = YaRecyclerAdapter(this)

fun List<KClass<out Fragment>>.pagerAdapter(activity: AppCompatActivity, boundless: Boolean = false)
        = YaFragmentPagerAdapter(activity.supportFragmentManager, this.map { it.java }, boundless)
