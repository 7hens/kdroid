package cn.thens.kdroid.core.util

/**
 * 位操作
 */
object Bits {
    /**
     * 完全匹配，如 matches(0x111, 0x110) == true
     */
    fun matches(value: Int, flag: Int): Boolean {
        return (value and flag) == flag
    }

    fun matches(value: Long, flag: Long): Boolean {
        return (value and flag) == flag
    }

    /**
     * 完全不匹配，如 differs(0x110, 0x001) == true
     */
    fun differs(value: Int, flag: Int): Boolean {
        return (value and flag) == 0
    }

    fun differs(value: Long, flag: Long): Boolean {
        return (value and flag) == 0L
    }

    /**
     * 添加，如 add(0x110, 0x011) == 0x111
     */
    fun add(value: Int, flag: Int): Int {
        return value or flag
    }

    fun add(value: Long, flag: Long): Long {
        return value or flag
    }

    /**
     * 移除，如 remove(0x110, 0x011) == 0x100
     */
    fun remove(value: Int, flag: Int): Int {
        return value and flag.inv()
    }

    fun remove(value: Long, flag: Long): Long {
        return value and flag.inv()
    }
}
