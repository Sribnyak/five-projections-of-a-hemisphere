package com.sribnyak.fiveprojections

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object Hemisphere {
    private const val N = 12
    private const val M = 36

    val lines = Array(N-1) { i ->
        val a = (i + 1) * PI / N
        Array(M+1) { j ->
            val b = j * PI / M
            Triple(-sin(a)*cos(b), cos(a), sin(a)*sin(b))
        }
    } + Array(N-1) { i ->
        val a = (i + 1) * PI / N
        Array(M+1) { j ->
            val b = j * PI / M
            Triple(-cos(a)*sin(b), -cos(b), sin(a)*sin(b))
        }
    }
}
