package cn.thens.kdroid.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import cn.thens.kdroid.core.vadapter.VFragmentPagerAdapter
import cn.thens.kdroid.sample.log.LogFragment
import cn.thens.kdroid.core.util.IdGenerator

class TestActivity : AppCompatActivity() {
    private val pagerAdapter by lazy { VFragmentPagerAdapter(supportFragmentManager) }

    private val fragment = LogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerAdapter.addAll(listOf(fragment))

        val viewPager = ViewPager(this)
        viewPager.id = IdGenerator.generateId()
        viewPager.adapter = pagerAdapter
        setContentView(viewPager)
    }
}