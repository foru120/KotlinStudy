package com.hongbog.customtext

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomText : AppCompatTextView {
    constructor(context: Context) : super(context) {

    }

    fun process(delimiter: String) {
        var one = text.substring(0, 4)
        var two = text.substring(4,6)
        var three = text.substring(6)

        setText("$one $delimiter $two $delimiter $three")
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typed = context.obtainStyledAttributes(attrs, R.styleable.CustomText)
        val size = typed.indexCount

        for (i in 0 until size) {
            when(typed.getIndex(i)) {
                R.styleable.CustomText_delimiter -> {
                    val delimiter = typed.getString(typed.getIndex(i)) ?: "-"
                    process(delimiter)
                }
            }
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }
}