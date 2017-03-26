package org.chx.kdroid.kadapter.adapter

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.AdapterView
import org.chx.kdroid.kadapter.HolderView
import org.chx.kdroid.kadapter.KAdapter
import kotlin.reflect.KClass

fun <D> HolderView.Factory<D>.adapt(view: RecyclerView) =
        YaRecyclerAdapter(this).apply { view.adapter = this }

fun <D> KAdapter<D>.adapt(view: AdapterView<*>) =
        YaListAdapter(this).apply { view.adapter = this }

fun <D> KAdapter<D>.adapt(view: ViewPager, boundless: Boolean = false) =
        YaPagerAdapter(this, boundless).apply {
            view.adapter = this
            view.currentItem = this.firstPosition
        }

fun List<KClass<out Fragment>>.adapt(view: ViewPager, boundless: Boolean = false) =
        (view.context as AppCompatActivity).supportFragmentManager.let {
            YaFragmentPagerAdapter(it, this.map { it.java }, boundless).apply {
                view.adapter = this
                view.currentItem = this.firstPosition
            }
        }
