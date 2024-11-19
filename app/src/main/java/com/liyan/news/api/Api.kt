package com.liyan.news.api

import com.liyan.news.model.NewsModel
import com.liyan.news.model.VideoModel
import retrofit2.http.GET

/**
 * Author : sun
 * Date: 2024/11/14 14:24
 * Description:
 */
interface Api {
    companion object {
        const val NEWS1 =
            "https://whyta.cn/api/tx/guonei?key=cc8cba0a7069&num={num}&page={page}&rand={rand}"  //国内新闻资讯热点事件

    }

    @GET("weibo")
    suspend fun getWeibo(): NewsModel

    @GET("baidu")
    suspend fun getBaidu(): NewsModel

    @GET("zhihu")
    suspend fun getZhihu(): NewsModel

    @GET("douyin_new")
    suspend fun getDouyin_new(): NewsModel

    @GET("douyin_music")
    suspend fun getDouyin_music(): NewsModel

    @GET("douban_new")
    suspend fun getDouban_new(): NewsModel

    @GET("douban_group")
    suspend fun getDouban_group(): NewsModel

    @GET("tieba")
    suspend fun getTieba(): NewsModel

    @GET("sspai")
    suspend fun sspai(): NewsModel

    @GET("thepaper")
    suspend fun thepaper(): NewsModel

    @GET("toutiao")
    suspend fun toutiao(): NewsModel

    @GET("36kr")
    suspend fun kr36(): NewsModel

    @GET("juejin")
    suspend fun juejin(): NewsModel

    @GET("newsqq")
    suspend fun newsqq(): NewsModel

    @GET("netease")
    suspend fun netease(): NewsModel

    @GET("lol")
    suspend fun lol(): NewsModel

    @GET("genshin")
    suspend fun genshin(): NewsModel

    @GET("weread")
    suspend fun weread(): NewsModel

    @GET("kuaishou")
    suspend fun kuaishou(): NewsModel

    @GET("netease_music_toplist")
    suspend fun netease_music_toplist(): NewsModel

    @GET("qq_music_toplist")
    suspend fun qq_music_toplist(): NewsModel

    @GET("ngabbs")
    suspend fun ngabbs(): NewsModel

    @GET("date")
    suspend fun date(): NewsModel

    @GET("https://api.kuleu.com/api/MP4_xiaojiejie?type=json")
    suspend fun xiaojiejie(): VideoModel
}