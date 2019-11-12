package cn.thens.kdroid.ml.util

import androidx.annotation.FloatRange
import java.util.*


class GaussianDistribution(private val mean: Double, @FloatRange(from = 0.0) private val variance: Double, private val random: Random = Random()) {
    init {
        if (variance < 0.0) {
            throw IllegalArgumentException("Variance must be non-negative value.")
        }
    }

    fun random(): Double {
        var r = 0.0
        while (r == 0.0) {
            r = random.nextDouble()
        }

        val c = Math.sqrt(-2.0 * Math.log(r))

        return if (random.nextDouble() < 0.5) {
            c * Math.sin(2.0 * Math.PI * random.nextDouble()) * variance + mean
        } else {
            c * Math.cos(2.0 * Math.PI * random.nextDouble()) * variance + mean
        }

    }

}