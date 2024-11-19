package com.liyan.news.viewmodel

import com.liyan.news.R

/**
 * Author : sun
 * Date: 2024/11/18 14:11
 * Description:
 */
class MineViewModel : BaseViewModel() {
    val privacy = "隐私政策"
    val agreement = "服务协议"
    val version = "检查更新"
    val list = mapOf(
        privacy to R.mipmap.wz_mine_privacy,
        agreement to R.mipmap.wz_mine_serve,
        version to R.mipmap.wz_mine_update,
    )
}