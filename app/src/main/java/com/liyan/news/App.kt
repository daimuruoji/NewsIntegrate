package com.liyan.news

import android.app.Application
import android.content.Context

lateinit var mApp: Context
    private set//全局ApplicationContext,set作用域设置为private防止外部修改

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mApp = this
    }
}
