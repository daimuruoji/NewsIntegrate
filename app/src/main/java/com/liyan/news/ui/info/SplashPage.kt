package com.liyan.news.ui.info

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liyan.news.MainActivity
import com.liyan.news.R
import com.liyan.news.view.CycleProgress
import com.liyan.news.viewmodel.SplashViewModel

/**
 * Author : sun
 * Date: 2024/11/19 09:59
 * Description:
 */
@Composable
fun SplashPage(canNext: Boolean, viewModel: SplashViewModel = viewModel()) {
    val context = LocalContext.current

    Scaffold(
        content = { paddingValues ->
            Box(Modifier.fillMaxSize()) {
                Image(
                    painterResource(R.mipmap.startpage_bg),
                    null,
                    Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 120.dp + paddingValues.calculateTopPadding()),
                    contentAlignment = Alignment.TopCenter // 设置内容对齐
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.startpage_head), // 替换为你的图像资源
                        contentDescription = "背景",
                        modifier = Modifier
                            .size(width = 320.dp, height = 170.dp), // 设置图像的宽度和高度
                        contentScale = ContentScale.Fit // 设置缩放类型为 fitXY
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp + paddingValues.calculateTopPadding(), end = 12.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.startpage_age_12), // 替换为你的小图像资源
                        contentDescription = "小图像",
                        modifier = Modifier
                            .size(width = 50.dp, height = 64.dp) // 设置图像宽高
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.BottomCenter)
                        .background(color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.4f)), // 设置背景和透明度
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.startpage_text), // 替换为你的文本图像资源
                        contentDescription = "文本图像",
                        modifier = Modifier
                            .width(310.dp)
                            .height(29.dp) // 设置图像宽高
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 120.dp, start = 30.dp, end = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "正在加载精彩内容...",
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                        }
                    )
                    CycleProgress(
                        modifier = Modifier.fillMaxWidth(), state = canNext
                    ) {
                        MainActivity.start(context = context)
                        (context as Activity).finish()
                    }
                }

            }

        }
    )

}