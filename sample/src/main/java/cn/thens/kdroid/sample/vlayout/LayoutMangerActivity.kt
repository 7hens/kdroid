package cn.thens.kdroid.sample.vlayout

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cn.thens.kdroid.sample.vadapter.MultiItemTypeAdapter

class LayoutMangerActivity : AppCompatActivity() {
    private val vAdapter by lazy { MultiItemTypeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView(this))
    }

    private fun createContentView(context: Context): View {
        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        return LinearLayout(context).apply {
            RecyclerView(context).apply {
                layoutManager = YaLayoutManager()
                adapter = vAdapter
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
            }.also { addView(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        vAdapter.refill()
    }

}