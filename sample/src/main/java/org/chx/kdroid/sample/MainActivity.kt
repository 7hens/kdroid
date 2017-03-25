package org.chx.kdroid.sample

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import org.chx.kandroid.R
import org.chx.kdroid.kadapter.KAdapter
import org.chx.kdroid.kadapter.adapter.listAdapter

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataList = (1..100).map { Pair("Title$it", "Description$it") }
        vListView.adapter = KAdapter.singleLayout(dataList, R.layout.item) { data, _ ->
            vTitle.text = data.first
            vDescription.text = data.second
        }.listAdapter()
    }
}
