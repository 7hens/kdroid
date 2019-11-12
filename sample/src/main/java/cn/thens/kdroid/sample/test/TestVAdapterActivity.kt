package cn.thens.kdroid.sample.test

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.thens.kdroid.sample.R
import cn.thens.kdroid.sample.common.app.BaseActivity
import cn.thens.kdroid.sample.vadapter.BaseSampleAdapter
import cn.thens.kdroid.sample.vadapter.CarouselAdapter
import cn.thens.kdroid.sample.vadapter.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.activity_vadapter.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestVAdapterActivity : BaseActivity() {
    private val multiItemTypeAdapter by lazy { MultiItemTypeAdapter() }
    private val carouselAdapter by lazy { CarouselAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vadapter)

        vRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, android.R.color.holo_red_light),
                ContextCompat.getColor(this, android.R.color.holo_green_light),
                ContextCompat.getColor(this, android.R.color.holo_blue_bright),
                ContextCompat.getColor(this, android.R.color.holo_orange_light)
        )

        vList.layoutManager = object : LinearLayoutManager(this) {
            override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
//                AppOwner.catchError { super.onLayoutChildren(recycler, state) }
            }
        }

        vMultiItemType.setOnClickListener { resolveAdapter(multiItemTypeAdapter) }
        vCarousel.setOnClickListener { resolveAdapter(carouselAdapter) }

        vMultiItemType.performClick()
    }

    private fun <T> resolveAdapter(adapter: BaseSampleAdapter<T>) {
        vList.adapter = adapter

        adapter.refill()
        vRefreshLayout.setOnRefreshListener {
            launch {
                delay(3000L)
                vRefreshLayout.isRefreshing = false
                adapter.refill()
            }
        }
    }
}

data class YaData(
        val isUser: Boolean,
        val title: String,
        val description: String, val detail: String
)