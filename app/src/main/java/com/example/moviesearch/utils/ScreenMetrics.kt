package com.example.moviesearch.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

object ScreenMetrics {
    private val apiLevelAbove29 = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
    private val apiLevelAbove23 = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getScreenHeightForLowerApiLevel(context: Context): Int {
        val metrics = context.getSystemService(WindowManager::class.java).currentWindowMetrics
        return metrics.bounds.height()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getScreenHeightForHigherApiLevel(context: Context): Int {
        val display =
            ContextCompat.getSystemService(context, WindowManager::class.java)?.defaultDisplay
        val metrics = if (display != null) {
            DisplayMetrics().also { display.getRealMetrics(it) }
        } else {
            Resources.getSystem().displayMetrics
        }
        return metrics.densityDpi
    }


    private fun getScreenHeight(context: Context): Int {
        return if (apiLevelAbove29) {
            getScreenHeightForLowerApiLevel(context)

        } else {
            if (apiLevelAbove23) getScreenHeightForHigherApiLevel(context) else 400
        }
    }

     fun convertScreenHeightToDp(context: Context): Int {
        val screenPixelDensity = context.resources.displayMetrics.density
        val dpValue = getScreenHeight(context) / screenPixelDensity
        return dpValue.toInt()
    }

}