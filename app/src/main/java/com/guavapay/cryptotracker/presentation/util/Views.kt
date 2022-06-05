package com.guavapay.cryptotracker.presentation.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.guavapay.cryptotracker.R

fun EditText.setTextViewDrawableColor(color: Int) {
    for (drawable in this.compoundDrawablesRelative) {
        drawable?.mutate()
        drawable?.colorFilter = PorterDuffColorFilter(
            color, PorterDuff.Mode.SRC_IN
        )
    }
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.setDrawableRightTouch(setClickListener: () -> Unit) {
    this.setOnTouchListener(View.OnTouchListener { v, event ->

        if (event.action == MotionEvent.ACTION_UP) {
            val textView = v as EditText
            if (event.x >= textView.width - textView.compoundPaddingEnd) {

                setClickListener()
                return@OnTouchListener true
            }
        }
        false
    })
}

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun dip2px(dip: Int): Int {
    return (dip * getScreenDensity() + 0.5f).toInt()
}

fun getScreenDensity(): Float {
    return try {
        Resources.getSystem().displayMetrics.density
    } catch (e: Exception) {
        DisplayMetrics.DENSITY_DEFAULT.toFloat()
    }
}

fun EditText.clearEditText() {
    this.setText("")
}

fun setSpanColor(span: SpannableString, color: Int, start: Int) {
    span.setSpan(
        ForegroundColorSpan(color),
        start,
        span.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun BottomNavigationView.disableTooltip() {
    val content: View = getChildAt(0)
    if (content is ViewGroup) {
        content.forEach {
            it.setOnLongClickListener {
                return@setOnLongClickListener true
            }
        }
    }
}