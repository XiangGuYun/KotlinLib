package com.kotlindemo.base.utils

import android.graphics.Color
import android.text.Html
import android.widget.TextView

interface TextViewUtils {

    val TextView.textString: String get()=text.toString()

    fun <T:TextView> T.text(text:String): T {
        this.text = text
        return this
    }

    fun <T:TextView> T.color(color:Int): T {
        setTextColor(color)
        return this
    }

    fun <T:TextView> T.color(color:String): T {
        setTextColor(Color.parseColor(color))
        return this
    }

    fun <T:TextView> T.size(size:Int): T {
        textSize = size.toFloat()
        return this
    }

    fun <T:TextView> T.html(text:String):T{
        setText(Html.fromHtml(text))
        return this
    }

}