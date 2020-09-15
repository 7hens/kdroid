package cn.thens.kdroid.view

import android.content.Context
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import cn.thens.logdog.Logdog
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.roundToInt

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

    fun getDrawableFromAttr(context: Context, attr: Int): Drawable? {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        val attribute = intArrayOf(attr)
        val typedArray = context.theme.obtainStyledAttributes(typedValue.resourceId, attribute)
        val drawable = typedArray.getDrawable(0)
        typedArray.recycle()
        return drawable
    }

    fun <V : View> onLaidOut(view: V, action: V.() -> Unit) {
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

    fun getVisibleWindowSize(context: Context): Size? {
        val displayMetrics = context.resources.displayMetrics
        val windowWidth = displayMetrics.widthPixels
        var windowHeight = displayMetrics.heightPixels
        val statusBarWidth: Int = ViewHelper.getStatusBarWidth(context)
        val statusBarHeight: Int = ViewHelper.getStatusBarHeight(context)
        //        if (windowWidth > statusBarWidth) {
//            windowWidth -= statusBarWidth;
//        }
        if (windowHeight > statusBarHeight) {
            windowHeight -= statusBarHeight
        }
        return Size(windowWidth, windowHeight)
    }

    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getStatusBarWidth(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_width", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getViewGlobalRect(view: View): Rect {
        val viewRect = Rect()
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val left = location[0]
        val top = location[1]
        viewRect[left, top, left + view.width] = top + view.height
        return viewRect
    }

    fun isPointInViewRect(view: View, x: Float, y: Float): Boolean {
        return getViewGlobalRect(view).contains(x.roundToInt(), y.roundToInt())
    }

    fun performPress(view: View, x: Float, y: Float): Boolean {
        if (view is ViewGroup) {
            val childCount = view.childCount
            for (i in 0 until childCount) {
                if (performPress(view.getChildAt(i), x, y)) {
                    return true
                }
            }
        }
        return if (isPointInViewRect(view, x, y)) {
            view.performClick()
        } else false
    }

}