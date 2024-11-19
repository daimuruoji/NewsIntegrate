package com.liyan.news.ui.info

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liyan.news.BuildConfig
import com.liyan.news.R
import com.liyan.news.view.TitleBar
import com.liyan.news.viewmodel.MineViewModel

/**
 * Author : sun
 * Date: 2024/11/18 14:04
 * Description:
 */
@Composable
fun MainPage(viewModel: MineViewModel = viewModel()) {
    val itemList = viewModel.list
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val appName = context.getString(context.applicationInfo.labelRes)
    val versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    Scaffold(topBar = {
        TitleBar(stringResource(id = R.string.mine_tab_title))
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() + 50.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Image(
                    painterResource(R.mipmap.ic_launcher), null,
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(60.dp))
                )
                Text(
                    text = appName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "v$versionName",
                    fontSize = 12.sp,
                    color = Color(0xFF808080),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }

            MineItem(itemList) { title, _ ->
                when (title) {
                    viewModel.privacy -> NewsDetailActivity.go(
                        context,
                        title,
                        BuildConfig.privacyUrl
                    )

                    viewModel.agreement -> NewsDetailActivity.go(
                        context,
                        title,
                        BuildConfig.agreementUrl
                    )

                    viewModel.version -> Toast.makeText(
                        context,
                        "已经是最新版本",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {

                    }
                }

            }
        }
    })
}


@Composable
fun MineItem(items: Map<String, Int>, click: ((String, Int) -> Unit)?) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items.forEach { item ->
            Column(modifier = Modifier
                .weight(1f)
                .height(100.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        click?.invoke(item.key, item.value)
                    }
                )) {
                Image(
                    painterResource(item.value), null,
                    Modifier
                        .width(34.dp)
                        .height(36.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = item.key, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}