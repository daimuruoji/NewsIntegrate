package com.liyan.news.ui.info

import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.liyan.news.R
import com.liyan.news.ext.State
import com.liyan.news.view.LoadingPage
import com.liyan.news.view.TitleBar
import com.liyan.news.viewmodel.VideoViewModel

/**
 * Author : sun
 * Date: 2024/11/15 16:33
 * Description:
 */
@Composable
fun VideoPage(viewModel: VideoViewModel = viewModel(), isShow: Boolean) {
    val context = LocalContext.current
    val state by viewModel.stateLiveData.observeAsState()
    val videoStr by viewModel.moviesLiveData.observeAsState()
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    // 监听 isVisible 的变化
    LaunchedEffect(isShow) {
        if (isShow) {
            println("组件已显示")
            // 可以在这里执行显示时的操作
            viewModel.stateLiveData.value = State.Loading
        } else {
            println("组件已隐藏")
            Jzvd.releaseAllVideos()
        }
    }
    Scaffold(topBar = {
        TitleBar(text = stringResource(id = R.string.recommend_movie_title))
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .background(color = Color.Black)
                .onGloballyPositioned {
                    containerSize = it.size
                }
        ) {
            LoadingPage(state = state!!, loadInit = { viewModel.getMovieLists() }) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Box(Modifier.fillMaxSize()) {
                        val width = containerSize.width
                        val height = containerSize.height
                        AndroidView(
                            factory = {
                                JzvdStd(context).apply {
                                    layoutParams = ViewGroup.LayoutParams(width, height)
                                }
                            },
                            Modifier
                                .align(Alignment.Center)
                        ) {
                            it.setUp(videoStr, "")
                            it.startVideoAfterPreloading()
                        }
                        BackArrowDown(click = {
                            viewModel.stateLiveData.value = State.Loading
                        })
                    }
                }
            }
        }
    })
}