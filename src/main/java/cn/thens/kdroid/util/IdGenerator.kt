package cn.thens.kdroid.util

import android.os.Build
import android.view.View
import java.util.concurrent.atomic.AtomicInteger

object IdGenerator {
    private val nextGeneratedId by lazy { AtomicInteger(1) }

    fun generateId(): Int {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            while (true) {
                val result = nextGeneratedId.get()
                var newValue = result + 1
                if (newValue > 0x00FFFFFF) newValue = 1
                if (nextGeneratedId.compareAndSet(result, newValue)) {
                    return result
                }
            }
        } else {
            return View.generateViewId()
        }
    }
}