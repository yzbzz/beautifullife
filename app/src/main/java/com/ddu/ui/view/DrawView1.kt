package com.ddu.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by yzbzz on 2018/4/8.
 */
class DrawView1 : View {

    val paint = Paint()
    val path = Path()

    init {
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(RectF(200f, 50f, 400f, 250f), -225f, 225f)
        path.arcTo(RectF(400f, 50f, 600f, 250f), -180f, 225f, false)
        path.lineTo(400f, 392f)
    }


    var y1 = 0f

    val rect = RectF()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.isAntiAlias = true
        drawPath(canvas)
        drawView(canvas)
    }

    private fun drawPath(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    private fun drawView(canvas: Canvas) {
        y1 += 100

        canvas.drawCircle(100f, y1, 90f, paint)
        canvas.drawColor(Color.parseColor("#88880000"))

        canvas.drawRect(300f, y1, 500f, 190f, paint)


        // point
        paint.strokeWidth = 20f
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawPoint(600f, y1, paint)

        paint.strokeCap = Paint.Cap.SQUARE
        canvas.drawPoint(700f, y1, paint)

        paint.strokeCap = Paint.Cap.BUTT
        canvas.drawPoint(800f, y1, paint)

        y1 += 100f
        // oval
        canvas.drawPoint(5f, y1 - 5, paint)
        rect.set(10f, y1, 500f, 400f)
        canvas.drawOval(rect, paint)
        canvas.drawPoint(500f, y1, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        rect.set(550f, y1, 1040f, 400f)
        canvas.drawOval(rect, paint)

        y1 += 200
        // roundRect
        paint.style = Paint.Style.FILL
        rect.set(10f, y1, 500f, 600f)
        canvas.drawRoundRect(rect, 50f, 50f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        rect.set(550f, y1, 1040f, 600f)
        canvas.drawRoundRect(rect, 50f, 50f, paint)


        // drawArc
        y1 += 200
        paint.style = Paint.Style.FILL

        canvas.drawPoint(5f, y1 - 5, paint)
        canvas.drawPoint(615f, y1 + 405, paint)

        rect.set(10f, y1, 610f, y1 + 400)
        canvas.drawArc(rect, -110f, 100f, true, paint) //绘制扇形

        canvas.drawArc(rect, 20f, 140f, false, paint) // 绘制弧形
        paint.setStyle(Paint.Style.STROKE); // 画线模式
        canvas.drawArc(rect, 180f, 60f, false, paint) // 绘制不封口的弧形

        y1 += 200
        rect.set(10f, y1, 100f, y1 + 200)

        // 用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
        canvas.save()
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        canvas.clipRect(rect.left, rect.top, rect.right, rect.bottom - 45f, Region.Op.INTERSECT)
        canvas.drawRoundRect(rect,45f,45f,paint)

        // 用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
        // save和restore要配对使用(restore可以比save少，但不能多)，如果restore调用次数比save多，会引发Error。save和restore之间，往往夹杂的是对Canvas的特殊操作
        canvas.restore()

        y1 += 300
    }
}