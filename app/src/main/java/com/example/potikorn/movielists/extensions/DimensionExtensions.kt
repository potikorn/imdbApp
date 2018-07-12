package com.example.potikorn.movielists.extensions

import android.content.Context
import android.util.TypedValue

infix fun Int.dpToPx(context: Context): Int =
    this * context.resources.displayMetrics.density.toInt()

infix fun Int.pxToDp(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
).toInt()