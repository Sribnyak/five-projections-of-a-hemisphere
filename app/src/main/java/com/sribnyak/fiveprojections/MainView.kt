package com.sribnyak.fiveprojections

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class MainView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private val path = Path()
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = 0xffffdd00.toInt()
    }

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) { }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }

    override fun surfaceDestroyed(holder: SurfaceHolder) { }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.reset()
                    path.moveTo(event.x, event.y)
                }
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                    path.lineTo(event.x, event.y)
                }
            }
            val canvas = holder.lockCanvas()
            canvas.drawColor(0xff000000.toInt())
            canvas.drawPath(path, paint)
            holder.unlockCanvasAndPost(canvas)
            return true
        }
        return super.onTouchEvent(event)
    }
}
