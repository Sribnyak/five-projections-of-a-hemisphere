package com.sribnyak.fiveprojections

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.min

class MainView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private lateinit var thread: WorkerThread
    private val projector = Projector()

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = 0xffffdd00.toInt()
    }

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = WorkerThread(holder, this)
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.setRunning(false)
        var retry = true
        while (retry) {
            try {
                thread.join()
                retry = false
            } catch (e: Exception) { }
        }
    }

    private fun scale(point: Pair<Double, Double>): Pair<Float, Float> {
        val factor = min(width, height) / 2.0 / 1.6
        return Pair(
            (width / 2.0 + point.first * factor).toFloat(),
            (height / 2.0 - point.second * factor).toFloat()
        )
    }

    private fun drawLines(canvas: Canvas) {
        for (line in Hemisphere.lines) {
            var point = scale(projector.project(line[0]))
            var x = point.first
            var y = point.second
            val path = Path()
            path.moveTo(x, y)
            for (i in 1 until line.size) {
                point = scale(projector.project(line[i]))
                x = point.first
                y = point.second
                path.lineTo(x, y)
            }
            canvas.drawPath(path, paint)
        }
    }

    fun update() {
        projector.update()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(0xff000000.toInt())
        drawLines(canvas)
    }
}
