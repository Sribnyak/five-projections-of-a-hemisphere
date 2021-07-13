package com.sribnyak.fiveprojections

import android.graphics.Canvas
import android.view.SurfaceHolder

class WorkerThread(
    private val surfaceHolder: SurfaceHolder,
    private val mainView: MainView
) : Thread() {
    private var running: Boolean = false

    private val targetFPS = 50

    fun setRunning(b: Boolean) { running = b }

    override fun run() {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        val targetTime = (1000 / targetFPS).toLong()

        while (running) {
            startTime = System.nanoTime()
            var canvas: Canvas? = null

            try {
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    this.mainView.update()
                    this.mainView.draw(canvas!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis

            try {
                sleep(waitTime)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
