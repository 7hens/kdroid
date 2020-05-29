package cn.thens.kdroid.view

import android.content.Context
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.widget.TextView
import cn.thens.logdog.Logdog
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author 7hens
 */
object ViewHelper {
    private val sNextGeneratedId = AtomicInteger(1)

    fun generateId(): Int {
        while (true) {
            val result = sNextGeneratedId.get()
            var newValue = result + 1
            if (newValue > 0x00FFFFFF) newValue = 1
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result
            }
        }
    }

    fun <V: View> onLaidOut(view: V, action: V.() -> Unit) {
        if (view.width != 0 || view.height != 0) {
            action.invoke(view)
            return
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                        action.invoke(view)
                    }
                })
    }

    fun setTextAutoWrap(view: TextView, rawText: String) {
        onLaidOut(view) {
            if (width == 0) {
                Logdog.get().error("view width is 0")
                return@onLaidOut
            }
            val textWidth = (width - paddingLeft - paddingRight).toFloat()
            val rawTextLines = rawText
                    .replace("\r", "")
                    .split("\n")
                    .dropLastWhile { it.isEmpty() }
            val newText = StringBuilder()
            for (rawTextLine in rawTextLines) {
                if (paint.measureText(rawTextLine) <= textWidth) {
                    newText.append(rawTextLine)
                } else {
                    var lineWidth = 0f
                    for (element in rawTextLine) {
                        val charWidth = paint.measureText(element.toString())
                        lineWidth += charWidth
                        if (lineWidth <= textWidth) {
                            newText.append(element)
                        } else {
                            newText.append("\n").append(element)
                            lineWidth = charWidth
                        }
                    }
                }
                newText.append("\n")
            }
            if (!rawText.endsWith("\n")) {
                newText.deleteCharAt(newText.length - 1)
            }
            text = newText
        }
    }

    fun getScaledTouchSlop(context: Context): Int {
        return ViewConfiguration.get(context).scaledTouchSlop
    }

    fun hasNavigationBar(context: Context): Boolean {
        val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        return !hasMenuKey && !hasBackKey
    }

    fun getNavigationBarHeight(context: Context): Int {
        val result = 0
        if (hasNavigationBar(context)) {
            val resources = context.resources
            val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            val resourceName = if (isPortrait) "navigation_bar_height" else if (isTabletDevice(context)) "navigation_bar_height_landscape" else "navigation_bar_width"
            val resourceId = resources.getIdentifier(resourceName, "dimen", "android")
            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    fun isTabletDevice(context: Context): Boolean {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun getDefaultWindowLayoutParams(): WindowManager.LayoutParams {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.gravity = Gravity.CENTER
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.flags = 0 or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_DIM_BEHIND or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        layoutParams.dimAmount = 0.6f
        layoutParams.windowAnimations = android.R.style.Animation_Toast
        return layoutParams
    }

}