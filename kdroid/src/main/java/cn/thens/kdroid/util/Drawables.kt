package cn.thens.kdroid.util

import android.content.Context
import android.graphics.*
import android.graphics.drawable.*
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import kotlin.math.max

@Suppress("MemberVisibilityCanBePrivate", "unused")
object Drawables {
    fun fromColor(color: Int): Drawable {
        return ColorDrawable(color)
    }

    fun fromColor(color: String): Drawable {
        return fromColor(Color.parseColor(color))
    }

    fun fromRes(context: Context, resId: Int): Drawable {
        return ContextCompat.getDrawable(context, resId)!!
    }

    fun fromAttr(context: Context, attr: Int): Drawable? {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        val attribute = intArrayOf(attr)
        val typedArray = context.theme.obtainStyledAttributes(typedValue.resourceId, attribute)
        val drawable = typedArray.getDrawable(0)
        typedArray.recycle()
        return drawable
    }

    fun fromBitmap(bitmap: Bitmap): Drawable {
        return BitmapDrawable(bitmap)
    }

    fun clone(drawable: Drawable): Drawable {
        return drawable.constantState!!.newDrawable()
    }

    fun tint(drawable: Drawable, color: Int): Drawable {
        val newDrawable = DrawableCompat.wrap(clone(drawable)).mutate()
        DrawableCompat.setTint(newDrawable, color)
        return newDrawable
    }

    fun selector(vararg items: Pair<IntArray, Drawable>): StateListDrawable {
        return StateListDrawable().apply {
            items.forEach { (stateSet, drawable) ->
                addState(stateSet, drawable)
            }
        }
    }

    fun selector(state: Int, drawable: Drawable, drawable2: Drawable): StateListDrawable {
        return selector(intArrayOf(state) to drawable, intArrayOf(-state) to drawable2)
    }

    fun shape(fn: GradientDrawable.() -> Unit): GradientDrawable {
        return GradientDrawable().apply {
            gradientType = GradientDrawable.LINEAR_GRADIENT
            fn()
        }
    }

    fun of(bitmap: Bitmap, draw: Drawable.(Canvas, Paint) -> Unit): Drawable {
        return Drawables.PaintDrawable.create(bitmap, draw)
    }

    fun circle(bitmap: Bitmap): Drawable {
        val width = bitmap.width
        val height = bitmap.height
        return of(bitmap) { canvas, paint ->
            canvas.drawCircle(width / 2F, height / 2F, max(width, height) / 2F, paint)
        }
    }

    fun rounded(bitmap: Bitmap, rx: Int, ry: Int): Drawable {
        val rectF = RectF()
        return of(bitmap) { canvas, paint ->
            bounds.run { rectF.set(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat()) }
            canvas.drawRoundRect(rectF, rx.toFloat(), ry.toFloat(), paint)
        }
    }

    private abstract class PaintDrawable(private val paint: Paint) : Drawable() {
        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun setColorFilter(cf: ColorFilter?) {
            paint.colorFilter = cf
        }

        override fun getOpacity(): Int {
            return PixelFormat.TRANSLUCENT
        }

        companion object {
            fun create(bitmap: Bitmap, draw: Drawable.(Canvas, Paint) -> Unit): PaintDrawable {
                val paint = Paint()
                paint.isAntiAlias = true
                paint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                return object : PaintDrawable(paint) {
                    override fun draw(canvas: Canvas) {
                        draw(canvas, paint)
                    }

                    override fun getIntrinsicWidth(): Int {
                        return bitmap.width
                    }

                    override fun getIntrinsicHeight(): Int {
                        return bitmap.height
                    }
                }
            }
        }
    }
}