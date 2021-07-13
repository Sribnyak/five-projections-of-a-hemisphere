package com.sribnyak.fiveprojections

import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sin
import kotlin.math.tan

class Projector {
    private val period = 50
    private var counter = 0
    private var state = -4
    private var direction = -1
    private val eps = 1e-7

    private fun getK(): Double = if (state % 2 == 0) {
        state / 4.0
    } else {
        ((state - direction) / 2 + direction * counter / period.toDouble()) / 2.0
    }

    private fun safeTan(x: Double): Double {
        return if (PI/2 - x > eps) tan(x) else tan(PI/2 - eps)
    }

    fun update() {
        counter += 1
        if (counter == period) {
            counter = 0
            if (state == 4 || state == -4)
                direction = -direction
            state += direction
        }
    }

    fun project(point: Triple<Double, Double, Double>): Pair<Double, Double> {
        val (x, y, z) = point
        val t = acos(z)
        if (sin(t) < eps)
            return Pair(x, y)
        val k = getK()
        val r = when {
            k < 0 -> sin(-k*t) / (-k)
            k > 0 -> safeTan(k*t) / k
            else -> t
        }
        return Pair(x * r / sin(t), y * r / sin(t))
    }

}
