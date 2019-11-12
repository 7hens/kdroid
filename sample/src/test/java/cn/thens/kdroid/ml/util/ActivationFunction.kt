package cn.thens.kdroid.ml.util

object ActivationFunction {
    fun step(x: Double): Int {
        return if (x >= 0) 1 else -1
    }
}