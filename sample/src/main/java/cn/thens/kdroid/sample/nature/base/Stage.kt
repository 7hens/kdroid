package cn.thens.kdroid.sample.nature.base

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import cn.thens.kdroid.core.io.AndroidMainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class Stage(context: Context, attrs: AttributeSet? = null) : View(context, attrs), AndroidMainScope {
    private var isStarted = false
    val children = ArrayList<Sprite>()

    init {
        launch {
            while (isStarted) {
                invalidate()
                delay(PERIOD)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        children.forEach { it.onDraw(canvas) }
        isStarted = true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancel()
    }

    companion object {
        const val PERIOD = 40L
        const val SPEED_FACTOR = PERIOD / 1000F
    }
}