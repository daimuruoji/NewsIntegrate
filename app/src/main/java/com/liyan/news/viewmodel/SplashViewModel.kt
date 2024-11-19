package com.liyan.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Author : sun
 * Date: 2024/11/19 10:00
 * Description:
 */
class SplashViewModel: ViewModel() {
    val showPop = MutableLiveData<Boolean>()
    val next = MutableLiveData<Boolean>()
}