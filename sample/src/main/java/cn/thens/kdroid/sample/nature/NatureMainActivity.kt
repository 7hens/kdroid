package cn.thens.kdroid.sample.nature

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import cn.thens.kdroid.sample.nature.walk.WalkerStage

class NatureMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)

        val stage = WalkerStage(this)
        setContentView(stage)
    }
}