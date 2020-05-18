package cn.thens.kdroid.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import cn.thens.kdroid.sample.log.LogFragment
import cn.thens.kdroid.util.IdGenerator

class TestActivity : AppCompatActivity() {

    private val fragment = LogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewPager = ViewPager(this)
        viewPager.id = IdGenerator.generateId()
        setContentView(viewPager)
    }
}