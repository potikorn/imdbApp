package com.example.potikorn.movielists.ui

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class MainViewPager : ViewPager {

    private var enabled: Boolean? = null

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.enabled == true) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.enabled == true) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    /**
     * Enable or disable the swipe navigation
     * @param enabled
     */
    fun setPagingEnabled(enabled: Boolean) {
        this.enabled = enabled
    }
}