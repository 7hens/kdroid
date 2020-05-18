package cn.thens.kdroid.util

/**
 * @author: 7hens
 * @date: 2018/12/24
 */
abstract class Mocker<T> private constructor() {
    abstract fun one(): T

    fun list(count: Int): List<T> {
        return (0 until count).map { one() }
    }

    companion object {
        @JvmStatic
        fun <T> of(fn: () -> T): Mocker<T> {
            return object : Mocker<T>() {
                override fun one(): T {
                    return fn()
                }
            }
        }

        @JvmStatic
        fun <T> oneOf(vararg elements: T): T {
            val randomIndex = Math.random().times(elements.size).toInt()
            return elements[randomIndex]
        }
    }
}