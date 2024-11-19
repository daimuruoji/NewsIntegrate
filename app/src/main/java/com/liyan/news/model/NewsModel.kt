package com.liyan.news.model

/**
 * Author : sun
 * Date: 2024/11/14 14:08
 * Description:
 */
open class BaseHttpModel {
    var code: Int = 0
    var msg: String = ""
}

data class NewsModel(
    val name: String = "",
    val title: String = "",
    val subtitle: String = "",
    val from: String = "",
    val total: String = "",
    val updateTime: String = "",
    val data: ArrayList<NewsItemModel> = arrayListOf(),
) : BaseHttpModel()

data class NewsItemModel(
    val title: String?,
    val desc: String?,
    val pic: String?,
    val hot: Long?,
    val url: String?,
    val mobileUrl: String?,
)