package cn.thens.kdroid.util


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.IdRes
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.max
import kotlin.math.min

@Suppress("unused")
object Bitmaps {

    fun fromDrawable(drawable: Drawable): Bitmap {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            draw(drawable, drawable.intrinsicWidth, drawable.intrinsicHeight)
        }
    }

    fun fromDrawable(drawable: Drawable, width: Int, height: Int): Bitmap {
        var w = width
        var h = height
        val originalWidth = drawable.intrinsicWidth
        val originalHeight = drawable.intrinsicHeight
        val scaleSize = min(1f * w / originalWidth, 1f * h / originalHeight)
        w = (scaleSize * originalWidth).toInt()
        h = (scaleSize * originalHeight).toInt()
        if (scaleSize == 1.0f) {
            return fromDrawable(drawable)
        }
        return if (drawable is BitmapDrawable) {
            zoom(drawable.bitmap, w, h)
        } else {
            draw(drawable, w, h)
        }
    }

    private fun draw(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(Canvas(bitmap))
        return bitmap
    }

    fun fromRes(resources: Resources, @IdRes resId: Int, width: Int, height: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, options)
        options.inSampleSize = computeInSampleSize(options.outWidth, options.outHeight, width, height)
        options.inJustDecodeBounds = false
        return fromRes(resources, resId, options)
    }

    fun fromRes(resources: Resources, @IdRes resId: Int, options: BitmapFactory.Options?): Bitmap {
        return BitmapFactory.decodeResource(resources, resId, options)
    }

    fun fromFile(file: File): Bitmap {
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    fun fromFile(file: File, options: BitmapFactory.Options): Bitmap {
        return BitmapFactory.decodeFile(file.absolutePath, options)
    }

    fun fromInputStream(inputStream: InputStream): Bitmap {
        return BitmapFactory.decodeStream(inputStream)
    }

    fun write(bitmap: Bitmap, out: OutputStream) {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    fun write(bitmap: Bitmap, file: File) {
        file.outputStream().use { write(bitmap, it) }
    }

    private fun computeInSampleSize(outWidth: Int, outHeight: Int, targetWith: Int, targetHeight: Int): Int {
        if (targetWith == 0 || targetHeight == 0) return 1
        if (outWidth <= targetWith || outHeight <= targetHeight) return 1
        val scaleSize = min(1.0 * outWidth / targetWith, 1.0 * outHeight / targetHeight)
        var inSampleSize = 1
        while (scaleSize > inSampleSize) {
            inSampleSize *= 2
        }
        return max(inSampleSize, 1)
    }

    fun zoom(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        val matrix = Matrix()
        val scaleWidth = 1F * width / w
        val scaleHeight = 1F * height / h
        val scaleSize = min(scaleWidth, scaleHeight)
        if (scaleSize >= 1) return bitmap
        matrix.postScale(scaleSize, scaleSize)
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true)
    }
}