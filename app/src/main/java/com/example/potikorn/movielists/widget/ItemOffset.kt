package com.example.potikorn.movielists.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemOffset(private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State?
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }
        val itemCount = state?.itemCount

        /** first position */
        if (itemPosition == 0) {
            outRect.set(padding * 2, 0, padding / 2, 0)
        }
        /** last position */
        else if (itemCount != null) {
            if (itemCount > 0 && itemPosition == itemCount - 1) {
                outRect.set(padding / 2, 0, padding * 2, 0)
            }
            /** positions between first and last */
            else {
                outRect.set(padding / 2, 0, padding / 2, 0)
            }
        }
    }
}