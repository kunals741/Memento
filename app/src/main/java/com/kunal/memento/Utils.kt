package com.kunal.memento

import android.content.Context

object Utils {
    fun dpToPx(context: Context, dp: Int): Int {
        return dp * context.resources.displayMetrics.density.toInt()
    }
}