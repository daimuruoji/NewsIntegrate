package com.liyan.news.ui.info

import android.widget.Toast
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liyan.news.R
import com.liyan.news.mApp
import com.liyan.news.view.TitleBar
import com.liyan.news.viewmodel.NewsViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Author : sun
 * Date: 2024/11/15 10:23
 * Description:
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsPages(viewModel: NewsViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val indexSelected by viewModel.newsSelectedIndex.observeAsState(initial = 0)
    val pagerState = rememberPagerState(
        pageCount = { viewModel.count },
        initialPage = indexSelected,
        initialPageOffsetFraction = 0f,
    )
    Scaffold(
        topBar = {
            TitleBar(stringResource(id = R.string.information_title))
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    containerColor = Color(0xff098765),
                    indicator = { positions ->//设置滑动条的属性，默认是白色的
                        PagerTabIndicator(positions, pagerState, height = 2.dp)
                    },
                    divider = {//设置底部的分割线
                        //Box(
                        //    Modifier
                        //        .fillMaxWidth()
                        //        .height(2.dp)
                        //        .background(Color.Cyan)
                        //) {
                        //
                        //}
                    }
                ) {
                    viewModel.titleItems.forEachIndexed { index, data ->
                        Tab(
                            modifier = Modifier.fillMaxHeight(),//这里必须设置高度，否则会展示不正常
                            selected = index == pagerState.currentPage,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            }) {
                            Text(
                                text = viewModel.titleItems[index],
                                style = TextStyle(color = Color.White),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp, 3.dp, 10.dp, 3.dp)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.weight(1f)
                    ) { page ->
                        NewsPage(page = page)
                    }
                }
            }
        })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTabIndicator(
    tabPositions: List<TabPosition>,
    pagerState: PagerState,
    color: Color = Color(0xFFFFFFFF),
    @FloatRange(from = 0.0, to = 1.0) percent: Float = 0.6f,
    height: Dp = 5.dp,
) {
    val currentPage by rememberUpdatedState(newValue = pagerState.currentPage)
    val fraction by rememberUpdatedState(newValue = pagerState.currentPageOffsetFraction)
    val currentTab = tabPositions[currentPage]
    val previousTab = tabPositions.getOrNull(currentPage - 1)
    val nextTab = tabPositions.getOrNull(currentPage + 1)
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {

            val indicatorWidth = currentTab.width.toPx() * percent
            val indicatorOffset = if (fraction > 0 && nextTab != null) {
                lerp(currentTab.left, nextTab.left, fraction).toPx()
            } else if (fraction < 0 && previousTab != null) {
                lerp(currentTab.left, previousTab.left, -fraction).toPx()
            } else {
                currentTab.left.toPx()
            }

            val canvasHeight = size.height
            drawRoundRect(
                color = color,
                topLeft = Offset(
                    indicatorOffset + (currentTab.width.toPx() * (1 - percent) / 2),
                    canvasHeight - height.toPx()
                ),
                size = Size(indicatorWidth + indicatorWidth * abs(fraction), height.toPx()),
                cornerRadius = CornerRadius(50f)
            )
        }
    )
}
