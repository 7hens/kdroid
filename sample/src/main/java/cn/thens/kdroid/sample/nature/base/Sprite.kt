package cn.thens.kdroid.sample.nature.base

import android.graphics.Canvas
import android.graphics.PointF
import androidx.annotation.CallSuper

interface Sprite {
    val stage: Stage
    val position: PointF
    val mas: Float
    val velocity: PointF
    val acceleration: Set<PointF>

    var x: Float
        get() = position.x
        set(value) {
            position.x = Math.min(stage.width.toFloat(), Math.max(0F, value))
        }

    var y: Float
        get() = position.y
        set(value) {
            position.y = Math.min(stage.height.toFloat(), Math.max(0F, value))
        }

    @CallSuper fun onDraw(canvas: Canvas) {
        var ax = 0F
        var ay = 0F
        acceleration.forEach { ax += it.x; ay += it.y }
        velocity.offset(ax * Stage.SPEED_FACTOR, ay * Stage.SPEED_FACTOR)
        x = position.x + velocity.x * Stage.SPEED_FACTOR
        y = position.y + velocity.y * Stage.SPEED_FACTOR
    }
}