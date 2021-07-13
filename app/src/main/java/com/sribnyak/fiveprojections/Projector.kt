package com.sribnyak.fiveprojections

class Projector {
    private val period = 50
    private var counter = 0
    private var state = 0
    private var direction = 1

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
        val k: Double = if (state % 2 == 0)
            state / 4.0
        else
            ((state - direction) / 2 + direction * counter / period.toDouble()) / 2.0
        return Pair((x+0.1*z)*(k/2+1), (y-0.05*z)*(k/2+1)) // TODO
    }

}
