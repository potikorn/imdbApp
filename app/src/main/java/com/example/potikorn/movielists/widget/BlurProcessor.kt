package com.example.potikorn.movielists.widget

import android.graphics.Bitmap
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.renderscript.Type
import android.support.annotation.NonNull

class BlurProcessor(private val rs: RenderScript) {

    @NonNull
    fun blur(bitmap: Bitmap?, radius: Float, repeat: Int): Bitmap? {
        var mRadius = radius

        if (!IS_BLUR_SUPPORTED) {
            return null
        }

        if (mRadius > MAX_RADIUS) {
            mRadius = MAX_RADIUS.toFloat()
        }

        val width = bitmap?.width
        val height = bitmap?.height

        // Create allocation type
        val bitmapType = Type.Builder(rs, Element.RGBA_8888(rs))
            .setX(width ?: 0)
            .setY(height ?: 0)
            .setMipmaps(false) // We are using MipmapControl.MIPMAP_NONE
            .create()

        // Create allocation
        var allocation: Allocation? = Allocation.createTyped(rs, bitmapType)

        // Create blur script
        var blurScript: ScriptIntrinsicBlur? = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        blurScript?.setRadius(mRadius)

        // Copy data to allocation
        allocation?.copyFrom(bitmap)

        // set blur script input
        blurScript?.setInput(allocation)

        // invoke the script to blur
        blurScript?.forEach(allocation)

        // Repeat the blur for extra effect
        for (i in 0 until repeat) {
            blurScript?.forEach(allocation)
        }

        // copy data back to the bitmap
        allocation?.copyTo(bitmap)

        // release memory
        allocation?.destroy()
        blurScript?.destroy()
        allocation = null
        blurScript = null

        return bitmap
    }

    companion object {
        private val IS_BLUR_SUPPORTED = Build.VERSION.SDK_INT >= 17
        private val MAX_RADIUS = 25
    }
}