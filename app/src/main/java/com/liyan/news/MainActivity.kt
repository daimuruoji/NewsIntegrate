package com.liyan.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.jzvd.Jzvd
import com.liyan.news.ui.info.MainPage
import com.liyan.news.ui.info.NewsPages
import com.liyan.news.ui.info.VideoPage
import com.liyan.news.ui.theme.NewsTheme
import com.liyan.news.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                Home()
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Jzvd.releaseAllVideos()
                finish()
            }
        })

    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(viewModel: MainViewModel = viewModel()) {
    val selectedIndex by viewModel.getSelectedIndex().observeAsState(initial = 0)
    val pagerState = rememberPagerState(
        pageCount = { 3 },
        initialPage = selectedIndex,
        initialPageOffsetFraction = 0f,
    )
    Scaffold(
        bottomBar = {
            BottomNavigationAlwaysShowLabelComponent(pagerState)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .verticalScroll(rememberScrollState())
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                    userScrollEnabled = false
                ) { page ->
                    Jzvd.releaseAllVideos()
                    when (page) {
                        0 -> NewsPages()
                        1 -> VideoPage(isShow = selectedIndex == 1)
                        2 -> MainPage()
                    }
                }
            }
        }
    )
}


val navigationItems = listOf(
    NavigationItem(mApp.getString(R.string.news_tab_title), Icons.Filled.Home),
    NavigationItem(mApp.getString(R.string.video_tab_title), Icons.Filled.DateRange),
    NavigationItem(mApp.getString(R.string.mine_tab_title), Icons.Filled.Person)
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationAlwaysShowLabelComponent(pagerState: PagerState) {
    val viewModel: MainViewModel = viewModel()
    val selectedIndex by viewModel.getSelectedIndex().observeAsState(0)
    val scope = rememberCoroutineScope()
    NavigationBar(
        containerColor = Color.White
    ) {
        navigationItems.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    viewModel.saveSelectIndex(index)
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
                icon = { // 图标
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = null
                    )
                },
                label = { // 文字
                    Text(text = navigationItem.title)
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    // 颜色配置
                    selectedIconColor = Color(0xff149ee7),
                    selectedTextColor = Color(0xff149ee7),
                    unselectedIconColor = Color(0xff999999),
                    unselectedTextColor = Color(0xff999999),
                    indicatorColor = Color.White,
                )
            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
)
