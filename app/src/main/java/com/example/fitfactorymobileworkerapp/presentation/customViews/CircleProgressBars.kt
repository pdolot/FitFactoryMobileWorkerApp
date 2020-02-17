package com.example.fitfactorymobileworkerapp.presentation.customViews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.fitfactorymobileworkerapp.R

class CircleProgressBars @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.primaryLight)
    }
    private var strokeWidth = 0
        set(value) {
            field = value
            paint.strokeWidth = field.toFloat()
        }

    private var strokeCap = 0
        set(value) {
            field = value
            paint.strokeCap = when (field) {
                0 -> Paint.Cap.ROUND
                1 -> Paint.Cap.BUTT
                2 -> Paint.Cap.SQUARE
                else -> Paint.Cap.ROUND
            }
        }

    private var progressCount = 1
    private var maxProgressAngle = 360

    private var spaceBetween = 0f

    private var bounds = ArrayList<RectF>()

    private var colorsRef: TypedArray? = null
    private var colors = ArrayList<Int>()

    private var bgColorsRef: TypedArray? = null
    private var bgColors = ArrayList<Int>()

    private var progressesRef: TypedArray? = null
    private var progresses = ArrayList<Float>()

    init {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleProgressBars,
            defStyleAttr,
            0
        )
        strokeWidth = a.getDimensionPixelSize(R.styleable.CircleProgressBars_strokeWidth, 0)
        spaceBetween =
            a.getDimensionPixelSize(R.styleable.CircleProgressBars_spaceBetween, 0).toFloat()
        strokeCap = a.getInteger(R.styleable.CircleProgressBars_strokeCap, 0)
        progressCount = a.getInteger(R.styleable.CircleProgressBars_count, 1)
        maxProgressAngle = a.getInteger(R.styleable.CircleProgressBars_maxProgressAngle, 360)

        colorsRef = resources.obtainTypedArray(
            a.getResourceId(
                R.styleable.CircleProgressBars_colors,
                R.array.colors
            )
        )
        bgColorsRef = resources.obtainTypedArray(
            a.getResourceId(
                R.styleable.CircleProgressBars_bgColors,
                R.array.bgColors
            )
        )
        progressesRef = resources.obtainTypedArray(
            a.getResourceId(
                R.styleable.CircleProgressBars_progresses,
                R.array.progresses
            )
        )

        colorsRef?.let {
            for (i in 0..it.length()) {
                colors.add(it.getColor(i, 0))
            }
        }

        bgColorsRef?.let {
            for (i in 0..it.length()) {
                bgColors.add(it.getColor(i, 0))
            }
        }

        progressesRef?.let {
            for (i in 0..it.length()) {
                val progress = it.getFloat(i, 0f)
                progresses.add(if (progress > 1f) 1f else if (progress < 0) 0f else progress)
            }
        }

        a.recycle()
        colorsRef?.recycle()
        bgColorsRef?.recycle()
        progressesRef?.recycle()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        for (i in 0..progressCount) {
            val space = strokeWidth / 2f + (i * (strokeWidth + spaceBetween))
            bounds.add(
                RectF(
                    space + paddingStart,
                    space + paddingTop,
                    measuredWidth.toFloat() - space - paddingEnd,
                    measuredHeight.toFloat() - space - paddingBottom
                )
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val startAngle = 90f + ((360f - maxProgressAngle) / 2f)

        for (i in 0..progressCount) {
            canvas.drawArc(bounds[i], startAngle, maxProgressAngle.toFloat(), false, paint.apply {
                color = bgColors[i]
            })

            canvas.drawArc(
                bounds[i],
                startAngle,
                maxProgressAngle * progresses[i],
                false,
                paint.apply {
                    color = colors[i]
                })
        }

    }
}