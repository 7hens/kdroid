package cn.thens.kdroid.view

import androidx.core.util.ObjectsCompat

class Size(val width: Int, val height: Int = width) {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other is Size) {
            return width == other.width && height == other.height
        }
        return false
    }

    override fun hashCode(): Int {
        return ObjectsCompat.hash(width, height)
    }

    override fun toString(): String {
        return "" + width + "x" + height
    }

    companion object {
        fun parseSize(string: String): Size {
            val splitText = string.split("x").toTypedArray()
            if (splitText.size != 2) {
                throw RuntimeException("only support format WxH")
            }
            val width = splitText[0].toInt()
            val height = splitText[1].toInt()
            return Size(width, height)
        }
    }

}