package com.liyan.news.utils

import android.content.Context
import android.content.SharedPreferences
import com.liyan.news.mApp

/**
 * Author : sun
 * Date: 2024/11/19 16:13
 * Description:
 */

object PreferencesHelper {
    private val prefs: SharedPreferences =
        mApp.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    var showPop: Boolean
        get() = prefs.getBoolean("show_pop", true)
        set(value) {
            prefs.edit().putBoolean("show_pop", value).apply()
        }
}