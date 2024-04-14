package com.sunmi.print.round

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import com.sunmi.print.R
import com.sunmi.print.round.parseAttribute

/**
 *    author : jogger
 *    date   : 9/2/21
 *    desc   :圆角ConstraintLayout
 */
class RoundLinearLayout(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val widget: GradientDrawable = GradientDrawable().parseAttribute(context, attrs)
    private var needChangeAlpha = false

    init {
        this.background = widget
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundLinearLayout)
        needChangeAlpha = a.getBoolean(R.styleable.RoundLinearLayout_needChangeAlpha, false)
        a.recycle()
    }
    fun setColors(colors: IntArray) {
        widget.colors = colors
    }

    override fun setBackgroundColor(color: Int) {
        widget.setColor(color)
    }
}