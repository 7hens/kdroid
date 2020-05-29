package cn.thens.kdroid.sample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import cn.thens.kdroid.sample.jbridge.JBridgeActivity
import cn.thens.kdroid.sample.launcher.LauncherMainActivity
import cn.thens.kdroid.sample.nature.NatureMainActivity
import cn.thens.kdroid.sample.test.TestRefreshActivity

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView(this))
    }

    private fun createContentView(context: Context): View {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL

            Button(context).apply {
                text = "VAdapter Demo"
            }.let { addView(it) }

            Button(context).apply {
                text = "Launcher"
                setOnClickListener { startActivity<LauncherMainActivity>() }
            }.let { addView(it) }

            Button(context).apply {
                text = "walk"
                setOnClickListener { startActivity<NatureMainActivity>() }
            }.let { addView(it) }

            Button(context).apply {
                text = "test refresh"
                setOnClickListener { startActivity<TestRefreshActivity>() }
            }.let { addView(it) }

            Button(context).apply {
                text = "jBridge"
                setOnClickListener { JBridgeActivity.start(context) }
            }.let { addView(it) }

            Button(context).apply {
                text = "main"
                setOnClickListener { startActivity<MainActivity>() }
            }.let { addView(it) }
        }
    }

    private inline fun <reified T> startActivity() {
        startActivity(Intent(this, T::class.java))
    }
}