package cn.thens.kdroid.sample.launcher

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.thens.kdroid.io.AndroidMainScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.floor

class LauncherMainActivity : AppCompatActivity(), CoroutineScope by AndroidMainScope() {
    private val cellGridAdapter by lazy { AppItemAdapter() }
    private lateinit var vTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView(this))
        showAppsStuff()
    }

    private fun showAppsStuff() {
        val apps = AppItemAdapter.getAppList(this)
        val list = ArrayList<ResolveInfo>()
        launch {
            repeat(100) {
                val needsAdd = it % (apps.size * 2) < apps.size
                val randomIndex = floor(Math.random() * list.size).toInt()
                if (needsAdd) {
                    list.add(randomIndex, apps[it % apps.size])
                } else {
                    list.removeAt(randomIndex)
                }
                setApps(list)
                delay(50L)
            }
        }
    }

    private fun createContentView(context: Context): View {
        val wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT
        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            vTitle = TextView(context).apply {
                text = "All apps"
                layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                }
            }.also { addView(it) }

            RecyclerView(context).apply {
                layoutManager = GridLayoutManager(context, 4)
                adapter = cellGridAdapter
                layoutParams = LinearLayout.LayoutParams(matchParent, matchParent)
            }.also { addView(it) }
        }
    }

    fun setApps(apps: List<ResolveInfo>) {
        cellGridAdapter.refill(apps)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LauncherMainActivity::class.java))
        }
    }
}
