package com.liyan.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fmt.compose.news.http.ApiService
import com.liyan.news.model.VideoModel

/**
 * Author : sun
 * Date: 2024/11/15 16:33
 * Description:
 */
class VideoViewModel : BaseViewModel() {
    val moviesLiveData = MutableLiveData<String>()
    fun getMovieLists() {
        launch {
            val movieModel = ApiService.xiaojiejie()
            moviesLiveData.value = movieModel.mp4_video
        }
    }
}