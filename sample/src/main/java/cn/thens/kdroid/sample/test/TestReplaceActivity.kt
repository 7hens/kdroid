package cn.thens.kdroid.sample.test

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.thens.kdroid.core.util.dp
import cn.thens.kdroid.sample.R
import cn.thens.kdroid.sample.common.view.color
import cn.thens.kdroid.sample.common.view.generateId

class TestReplaceActivity : AppCompatActivity() {
    lateinit var vTarget: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView(this))
    }

    private fun createContentView(context: Context): View {
        val wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT
        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        return RelativeLayout(context).apply {
            val vLabel = TextView(context).apply {
                generateId()
                layoutParams = RelativeLayout.LayoutParams(wrapContent, dp(56)).apply {
                    addRule(RelativeLayout.CENTER_IN_PARENT)
                }
                text = "如果要替换的视图的 LayoutParams 是 matchParent 或是 固定值，那么替换是可以成功的。但如果是 wrapContent 则会替换不成功（前后的宽高不一致）"
                gravity = Gravity.CENTER
            }.also { addView(it) }

            TextView(context).apply {
                layoutParams = RelativeLayout.LayoutParams(wrapContent, wrapContent).apply {
                    addRule(RelativeLayout.CENTER_HORIZONTAL)
                    addRule(RelativeLayout.BELOW, vLabel.id)
                }
                vTarget = this
                setPadding(32, 32, 32, 32)
                setBackgroundColor(color(R.color.colorPrimary))
                setOnClickListener {
                    replaceViewWithIndex(loadingView, vTarget)
                }
            }.also { addView(it) }
        }
    }

    val loadingView: View by lazy {
        val context = this
        LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(color(R.color.colorAccent))
            setOnClickListener {
                replaceViewWithIndex(vTarget, loadingView)
            }
        }
    }

    private fun replaceViewWithIndex(over: View, below: View) {
        val parent = below.parent as? ViewGroup ?: return
        val layoutParams = below.layoutParams
        val viewIndex = parent.indexOfChild(below)
        parent.removeViewAt(viewIndex)
        parent.addView(over, viewIndex, layoutParams)
    }
}