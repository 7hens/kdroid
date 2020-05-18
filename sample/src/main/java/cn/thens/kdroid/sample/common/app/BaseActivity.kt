package cn.thens.kdroid.sample.common.app

import androidx.appcompat.app.AppCompatActivity
import cn.thens.kdroid.io.AndroidMainScope
import kotlinx.coroutines.cancel

/**
 * @author 7hens
 */
abstract class BaseActivity: AppCompatActivity(), AndroidMainScope {
    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}