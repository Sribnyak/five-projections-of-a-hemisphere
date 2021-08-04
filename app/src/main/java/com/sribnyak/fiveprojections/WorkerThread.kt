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
        val targetTime = (1000 / targetFPS).toLong()
        var canvas: Canvas? = null
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long

        while (running) {
            startTime = System.nanoTime()

            try {
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    this.mainView.update()
                    this.mainView.draw(canvas!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas)
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis
            if (waitTime > 0)
                sleep(waitTime)
        }
    }
}
