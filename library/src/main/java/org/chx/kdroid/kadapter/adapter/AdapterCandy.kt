package org.chx.kdroid.kadapter.adapter

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import org.chx.kdroid.kadapter.AdapterDelegate
import org.chx.kdroid.kadapter.HolderView
import kotlin.reflect.KClass

fun <D> AdapterDelegate<D>.listAdapter() = YaListAdapter(this)

fun <D> AdapterDelegate<D>.pagerAdapter(boundless: Boolean = false) = YaPagerAdapter(this, boundless)

fun <D> HolderView.Factory<D>.recyclerAdapter() = YaRecyclerAdapter(this)

fun List<KClass<out Fragment>>.pagerAdapter(activity: AppCompatActivity, boundless: Boolean = false)
        = YaFragmentPagerAdapter(activity.supportFragmentManager, this.map { it.java }, boundless)
