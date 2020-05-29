package cn.thens.kdroid.sample.test

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import cn.thens.kdroid.sample.R
import cn.thens.kdroid.sample.common.app.BaseActivity
import cn.thens.kdroid.sample.launcher.AppItemAdapter
import kotlinx.android.synthetic.main.activity_test_refresh.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestRefreshActivity : BaseActivity() {
    private val appItemAdapter by lazy { AppItemAdapter() }
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_refresh)

        vList.adapter = appItemAdapter
        vList.layoutManager = GridLayoutManager(this, 4)
        appItemAdapter.refill(AppItemAdapter.getAppList(this))
        vRefreshLayout.setOnRefreshListener {
            launch {
                delay(1000L)
                val appList = AppItemAdapter.getAppList(context)
                appItemAdapter.refill(appList)
                vRefreshLayout.finishRefresh()
            }
        }
        vRefreshLayout.setOnLoadmoreListener {
            launch {
                delay(1000L)
                val appList = AppItemAdapter.getAppList(context)
                appItemAdapter.addAll(appList)
                appItemAdapter.notifyDataSetChanged()
                vRefreshLayout.finishLoadmore()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun testError() {
        Thread { makeError() }.start()
        makeError()
    }

    fun makeError() {
        val list = ArrayList<Int>()
        list[1]
    }
}