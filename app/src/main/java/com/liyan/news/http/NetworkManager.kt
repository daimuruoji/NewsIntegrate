package com.fmt.compose.news.http

import android.util.Log
import com.liyan.news.api.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val DEFAUULT_URI = "https://hot.cigh.cn/"
private val TAG = "NetworkManager"

private val okHttpClient by lazy {
    OkHttpClient.Builder()
        .apply {
            addInterceptor(HttpLoggingInterceptor { message -> Log.e(TAG, message) }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        .build()
}

private val retrofit by lazy {
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(
            DEFAUULT_URI
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

object ApiService : Api by retrofit.create(
    Api::
    class.java
)
