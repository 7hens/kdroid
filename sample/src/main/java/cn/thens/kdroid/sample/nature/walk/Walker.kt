package cn.thens.kdroid.sample.nature.walk

import android.graphics.*
import cn.thens.kdroid.sample.nature.base.Sprite
import cn.thens.kdroid.sample.nature.base.Stage
import java.util.*


class Walker(override val stage: Stage) : Sprite {
    override val position = PointF()
    override val velocity = PointF()
    override val mas = 1F
    override val acceleration = mutableSetOf<PointF>()

    init {
        stage.post {
            this.position.set(stage.width / 2F, stage.height / 2F)
        }
    }

    private val random by lazy { Random() }
    private val path by lazy { Path() }
    private val track = mutableListOf<PointF>()
    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = random.nextFloat() * 16F + 8F
            strokeCap = Paint.Cap.ROUND
            pathEffect = CornerPathEffect(32F)
            style = Paint.Style.STROKE
            shader = RadialGradient(randomFloat(400), randomFloat(400), randomFloat(200), randomColor(), randomColor(), Shader.TileMode.MIRROR)
        }
    }

    /**
     * -8 => 0x1000 => 0x0111 => 0x1000, 0x11_1000
     * -7 => 0x0111 => 0x1000 => 0x1001, 0x11_1001
     * -1 => 0x0001 => 0x1110 => 0x1111, 0x11_1111
     */
    private val Int.colorLong get() = toLong() and Int.MAX_VALUE.toLong().shl(1).plus(1)

    private fun randomColor(): Int {
        return (random.nextFloat() * Color.WHITE.colorLong).toLong().or(Long.MIN_VALUE).toInt()
    }

    private fun randomFloat(range: Int): Float = random.nextFloat() * range.toFloat()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        addTrack(x, y)
        path.reset()
        track.forEachIndexed { index, point ->
            if (index == 0) {
                path.moveTo(point.x, point.y)
            } else {
                path.lineTo(point.x, point.y)
            }
        }
        canvas.drawPath(path, paint)
        val margin = 16F
        if (x < margin && velocity.x < 0 || x > stage.width.minus(margin) && velocity.x > 0) {
            velocity.x = -velocity.x * 0.75F
        }
        if (y < margin && velocity.y < 0 || y > stage.height.minus(margin) && velocity.y > 0) {
            velocity.y = -velocity.y * 0.75F
        }
        stepGaussian()
    }

    private fun addTrack(x: Float, y: Float) {
        if (track.size >= 16) {
            val pos = track.removeAt(0)
            pos.set(x, y)
            track.add(pos)
        } else {
            track.add(PointF(x, y))
        }
    }

    private fun stepGaussian() {
        val speedSize = random.nextGaussian() * 64 + 128
        val randomX = (random.nextFloat() - 0.5F) * speedSize.toFloat()
        val randomY = (random.nextFloat() - 0.5F) * speedSize.toFloat()
        velocity.offset(randomX, randomY)
    }

    fun stepNoise() {
    }
}