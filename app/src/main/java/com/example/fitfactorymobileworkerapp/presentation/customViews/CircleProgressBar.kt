package com.example.fitfactorymobileworkerapp.presentation.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.fitfactorymobileworkerapp.R

class CircleProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var pieceCount: Int = 8
    private var strokeWidth: Float = 10f
    private var spacePercent: Int = 20
    private var arcAngle: Float = 0f
    private var bgColor: Int = 0
        set(value) {
            field = value
            backgroundPaint = Paint().apply {
                isAntiAlias = true
                color = this@CircleProgressBar.bgColor
                strokeCap = Paint.Cap.ROUND
                style = Paint.Style.STROKE
                strokeWidth = this@CircleProgressBar.strokeWidth
            }
            invalidate()
        }
    var color: Int = 0
        set(value) {
            field = value
            colorPaint = Paint().apply {
                isAntiAlias = true
                color = this@CircleProgressBar.color
                strokeCap = Paint.Cap.ROUND
                style = Paint.Style.STROKE
                strokeWidth = this@CircleProgressBar.strokeWidth
            }
            invalidate()
        }

    var progress: Float = 0f
        set(value) {
            field = if (value > 1f) 1f else if (value < 0) 0f else value
            progressAngle = (360f - spacePercent / 2) * progress
            setColor()
            invalidate()
        }

    private fun setColor() {
        when(progress){
            in 0f..0.33f -> {
                color = ContextCompat.getColor(context, R.color.positiveLight)
            }
            in 0.34f..0.66f -> {
                color = ContextCompat.getColor(context, R.color.primaryLight)
            }
            in 0.67f..1f -> {
                color = ContextCompat.getColor(context, R.color.negativeLight)
            }
        }
    }

    private var progressAngle = 0f
    private var pieceAngles: ArrayList<ArcAngles> = ArrayList()
    private lateinit var backgroundPaint: Paint
    private lateinit var colorPaint: Paint
    private lateinit var vb: Bound // view bound

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar,defStyleAttr, 0)
        pieceCount = a.getInteger(R.styleable.CircleProgressBar_pieceCount, 4)
        strokeWidth = a.getDimension(R.styleable.CircleProgressBar_strokeWidth, 10f)
        spacePercent = a.getInteger(R.styleable.CircleProgressBar_spacePercent, 10)
        bgColor = a.getColor(
            R.styleable.CircleProgressBar_backgroundColor,
            ContextCompat.getColor(context, R.color.colorPrimaryDark)
        )
        color = a.getColor(
            R.styleable.CircleProgressBar_color,
            ContextCompat.getColor(context, R.color.colorAccent)
        )
        progress = a.getFloat(R.styleable.CircleProgressBar_progress, 0f)
        a.recycle()

        arcAngle = (360 - (pieceCount * spacePercent)) / pieceCount.toFloat()

        for (i in 0 until pieceCount) {
            pieceAngles.add(ArcAngles().apply {
                startAngle = (spacePercent / 2f) + (i * (arcAngle + spacePercent))
                endAngle = startAngle + arcAngle
            })
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        vb = Bound(
            (paddingLeft + strokeWidth / 2).toInt(),
            (paddingTop + strokeWidth / 2).toInt(),
            measuredWidth - (paddingRight + strokeWidth / 2).toInt(),
            measuredHeight - (paddingBottom + strokeWidth / 2).toInt()
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var fullPiece = 0
        for (i in 0 until pieceCount) {
            canvas.drawArc(
                vb.left.toFloat(),
                vb.top.toFloat(),
                vb.right.toFloat(),
                vb.bottom.toFloat(),
                (spacePercent / 2f) + (i * (arcAngle + spacePercent)),
                arcAngle,
                false,
                if (progressAngle >= pieceAngles[i].endAngle) {
                    fullPiece = i + 1
                    colorPaint
                } else backgroundPaint
            )
        }
        if (fullPiece != pieceCount){
            if (progressAngle in pieceAngles[fullPiece].startAngle..pieceAngles[fullPiece].endAngle) {
                canvas.drawArc(
                    vb.left.toFloat(),
                    vb.top.toFloat(),
                    vb.right.toFloat(),
                    vb.bottom.toFloat(),
                    pieceAngles[fullPiece].startAngle,
                    progressAngle - pieceAngles[fullPiece].startAngle,
                    false,
                    colorPaint
                )
            }
        }
    }

    inner class ArcAngles {
        var startAngle: Float = 0f
        var endAngle: Float = 0f
    }

    class Bound(var left: Int, var top: Int, var right: Int, var bottom: Int)
}