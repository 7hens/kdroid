package cn.thens.kdroid.sample.vlayout

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cn.thens.kdroid.core.util.Logdog


class YaLayoutManager : RecyclerView.LayoutManager() {
    private var firstVisiblePosition = 0
    private var lastVisiblePosition = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        if (itemCount <= 0 || state.isPreLayout) return
        lastVisiblePosition = itemCount - 1
        recycleAndFillChildren(recycler, state, 0)
    }

    private fun recycleAndFillChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State, dy: Int): Int {
        Logdog.warn("isPreLayout = ${state.isPreLayout}\nitemCount = $itemCount\nchildCount = $childCount")
        var leftOffset = paddingLeft
        var topOffset = paddingTop
        var maxLineHeight = 0
        for (i in firstVisiblePosition..lastVisiblePosition) {
            val child = recycler.getViewForPosition(i)
            addView(child)
            measureChildWithMargins(child, 0, 0)
            val boxWidth = getDecoratedMeasurementHorizontal(child)
            val boxHeight = getDecoratedMeasurementVertical(child)
            if (leftOffset + boxWidth <= horizontalSpace) {
                layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + boxWidth, topOffset + boxHeight)
                leftOffset += boxWidth
                maxLineHeight = Math.max(maxLineHeight, boxHeight)
            } else {
                leftOffset = paddingLeft
                topOffset += maxLineHeight
                maxLineHeight = 0
                if (topOffset - dy <= verticalSpace) {
                    layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + boxWidth, topOffset + boxHeight)
                    leftOffset += boxWidth
                    maxLineHeight = Math.max(maxLineHeight, boxHeight)
                } else {
                    removeAndRecycleView(child, recycler)
                    lastVisiblePosition = i - 1
                    break
                }
            }
        }
        return dy
    }

    fun getDecoratedMeasurementHorizontal(view: View): Int {
        val params = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedMeasuredWidth(view) + params.leftMargin + params.rightMargin
    }

    fun getDecoratedMeasurementVertical(view: View): Int {
        val params = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedMeasuredHeight(view) + params.topMargin + params.bottomMargin
    }

    val verticalSpace: Int get() = height - paddingTop - paddingBottom

    val horizontalSpace: Int get() = width - paddingLeft - paddingRight

    override fun canScrollVertically(): Boolean {
        return false
    }

    private var verticalOffset = 0

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        if (dy == 0 || childCount == 0) return 0
        var offset = dy
        if (verticalOffset + dy < 0) {
            offset = -verticalOffset
        } else if (offset > 0) {
            val lastChild = getChildAt(childCount - 1)!!
            if (getPosition(lastChild) == itemCount - 1) {
                val gap = height - paddingBottom - getDecoratedBottom(lastChild)
                offset = when {
                    gap >= 0 -> -gap
                    else -> Math.min(offset, -gap)
                }
            }
        }
        offset = recycleAndFillChildren(recycler, state, offset)
        verticalOffset += offset
        offsetChildrenVertical(-offset)
        return offset
    }
}