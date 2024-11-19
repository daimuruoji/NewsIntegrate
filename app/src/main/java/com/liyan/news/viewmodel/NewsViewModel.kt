package com.liyan.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fmt.compose.news.http.ApiService
import com.liyan.news.ext.State
import com.liyan.news.model.NewsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Author : sun
 * Date: 2024/11/14 14:05
 * Description:
 */
class NewsViewModel : BaseViewModel() {
    val newsSelectedIndex = MutableLiveData<Int>()


    val newsDataMap by lazy {
        val map = hashMapOf<Int, MutableLiveData<NewsModel>>()
        (0..30).forEach {
            map[it] = MutableLiveData()
        }
        map
    }
    val stateLiveDataMap by lazy {
        val map = hashMapOf<Int, MutableLiveData<State>>()
        (0..30).forEach {
            map[it] = MutableLiveData()
        }
        map
    }

    fun launchBack(index: Int, block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                block()
            }.onSuccess {
                stateLiveDataMap[index]!!.value = State.Success
            }.onFailure {
                stateLiveDataMap[index]!!.value = State.Error(it.message)
            }
        }
    }

    val titleItems = listOf(
        //"微博",
        "今日头条",
        "知乎",
        "百度",
        "抖音热点榜",
        "抖音热歌榜",
        "豆瓣新片榜",
        "百度贴吧",
        "少数派",
        "澎湃新闻",
        "36氪",
        "稀土掘金",
        "腾讯新闻",
        "网易新闻",
        "英雄联盟",
        "原神",
        "微信读书",
        "NGA",
    )
    val count = titleItems.size
    fun getNewsLists(indx: Int) {
        launchBack(index = indx) {
            val newsModel = when (indx) {
                1 -> ApiService.getZhihu()
                2 -> ApiService.getBaidu()
                3 -> ApiService.getDouyin_new()
                4 -> ApiService.getDouyin_music()
                5 -> ApiService.getDouban_new()
                6 -> ApiService.getTieba()
                7 -> ApiService.sspai()
                8 -> ApiService.thepaper()
                9 -> ApiService.kr36()
                10 -> ApiService.juejin()
                11 -> ApiService.newsqq()
                12 -> ApiService.netease()
                13 -> ApiService.lol()
                14 -> ApiService.genshin()
                15 -> ApiService.weread()
                19 -> ApiService.ngabbs()
                else -> {
                    //ApiService.getWeibo()
                    ApiService.toutiao()
                }
            }
            newsDataMap[indx]!!.value = newsModel
        }
    }
}