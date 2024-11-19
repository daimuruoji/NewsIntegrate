package com.liyan.news.ui.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.liyan.news.ext.State
import com.liyan.news.ext.toPx
import com.liyan.news.model.NewsItemModel
import com.liyan.news.view.LoadingPage
import com.liyan.news.viewmodel.NewsViewModel

/**
 * Author : sun
 * Date: 2024/11/14 14:04
 * Description:
 */

@Composable
fun NewsPage(viewModel: NewsViewModel = viewModel(), page: Int = 0) {
    val state by viewModel.stateLiveDataMap[page]!!.observeAsState(State.Loading)
    val newsModel by viewModel.newsDataMap[page]!!.observeAsState()
    Column(Modifier.fillMaxSize()) {
        LoadingPage(state = state!!, loadInit = {
            viewModel.getNewsLists(page)
        }, contentView = {
            LazyColumn(
                modifier = Modifier
            ) {
                val stories = newsModel!!.data
                itemsIndexed(stories) { index, item ->
                    NewsItem(item)
                    if (index != stories.size - 1) {
                        Divider(
                            thickness = 0.5.dp, modifier = Modifier.padding(8.dp, 0.dp)
                        )
                    }
                }
            }
        })

    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsItem(item: NewsItemModel) {
    val context = LocalContext.current
    Row(
        Modifier
            .padding(10.dp)
            .clickable {
                NewsDetailActivity.go(context, item.title ?: "", item.mobileUrl ?: "")
            }) {
        Image(
            painter = rememberImagePainter(item.pic, builder = {
                crossfade(true)
                transformations(RoundedCornersTransformation(2.toPx().toFloat()))
            }),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(120.dp)
                .height(80.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(
                item.title ?: "",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 3.dp, 0.dp, 0.dp)
            )
            Text(
                item.desc ?: "",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(10.dp, 5.dp, 0.dp, 0.dp)
            )
        }
    }
}