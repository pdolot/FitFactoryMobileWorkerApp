package com.example.fitfactorymobileworkerapp.presentation.customViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.fitfactorymobileworkerapp.R
import java.lang.StringBuilder

class RoundedAnimatedView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var bgColor = 0
        set(value) {
            field = value
            bgPaint.color = value
            invalidate()
        }

    private var bgPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    private var maxRadius = 0
        set(value) {
            field = value
            radius = field.toFloat()
        }
    private var minRadius = 0
    private var roundedCorners = 0
    private var radius = 0f
        set(value) {
            field = value
            setRadii(radius)
        }

    var scale = 1.0f
        set(value) {
            field = value
            radius = maxRadius * field
        }

    private var radii: FloatArray? = null
    private var binary: String = "0000"
    private var viewBound = RectF()

    //shadow
    private var shadowRadius = 0f
    private var shadowDx = 0f
    private var shadowDy = 0f
    private var shadowColor = 0

    init {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RoundedAnimatedView,
            defStyleAttr,
            0
        )
        bgColor = a.getColor(
            R.styleable.RoundedAnimatedView_backgroundColor,
            ContextCompat.getColor(context, R.color.colorPrimary)
        )
        roundedCorners = a.getInteger(R.styleable.RoundedAnimatedView_roundedCorners, 0)

        if (roundedCorners > 15){
            roundedCorners = 15
        }
        binary = roundedCorners.toString(2)
        binary = binary.padStart(4, '0')

        maxRadius = a.getDimensionPixelSize(R.styleable.RoundedAnimatedView_maxRadius, 0)
        minRadius = a.getDimensionPixelSize(R.styleable.RoundedAnimatedView_minRadius, 0)
        shadowRadius = a.getDimensionPixelSize(R.styleable.RoundedAnimatedView_shadowRadius, 0).toFloat()
        shadowDx = a.getDimensionPixelSize(R.styleable.RoundedAnimatedView_shadowDx, 0).toFloat()
        shadowDy = a.getDimensionPixelSize(R.styleable.RoundedAnimatedView_shadowDy, 0).toFloat()
        shadowColor = a.getColor(
            R.styleable.RoundedAnimatedView_shadowColor,
            ContextCompat.getColor(context, R.color.transparent)
        )

        bgPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)

        a.recycle()
    }

    private fun setRadii(radii: Float){
        this.radii = floatArrayOf(
            if (binary[3] == '0') 0f else radii,
            if (binary[3] == '0') 0f else radii,
            if (binary[2] == '0') 0f else radii,
            if (binary[2] == '0') 0f else radii,
            if (binary[1] == '0') 0f else radii,
            if (binary[1] == '0') 0f else radii,
            if (binary[0] == '0') 0f else radii,
            if (binary[0] == '0') 0f else radii
        )
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setBounds()
    }

    private fun setBounds() {
        viewBound = RectF(
            0f + paddingStart,
            0f + paddingTop,
            measuredWidth.toFloat() - paddingEnd,
            measuredHeight.toFloat() - paddingBottom
        )
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val path = Path()
        path.addRoundRect(
            viewBound, radii, Path.Direction.CW
        )

        canvas.drawPath(path, bgPaint)
    }
}