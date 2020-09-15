package cn.thens.kdroid.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.core.content.ContextCompat

@Suppress("MemberVisibilityCanBePrivate", "unused")
object Colors {
    fun of(color: String): Int {
        return Color.parseColor(color)
    }

    fun of(context: Context, resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    fun selector(vararg items: Pair<IntArray, Int>): ColorStateList {
        val states = Array(items.size) { items[it].first }
        val colors = IntArray(items.size) { items[it].second }
        return ColorStateList(states, colors)
    }

    fun selector(state: Int, color: Int, color2: Int): ColorStateList {
        return selector(intArrayOf(state) to color, intArrayOf(-state) to color2)
    }
}