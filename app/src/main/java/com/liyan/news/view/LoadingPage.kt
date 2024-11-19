package com.liyan.news.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.liyan.news.R
import com.liyan.news.ext.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingPage(
    state: State,
    loadInit: (() -> Unit)? = null,
    contentView: @Composable BoxScope.() -> Unit,
) {
    val statePull = rememberPullToRefreshState()
    if (statePull.isRefreshing) {
        loadInit?.invoke()
    }
    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(statePull.nestedScrollConnection),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading() -> {
                loadInit?.invoke()
                CircularProgressIndicator()
            }

            state.isError() -> {
                statePull.endRefresh()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable {
                            loadInit?.invoke()
                        }
                ) {
                    Image(painterResource(R.mipmap.ic_no_network), null, Modifier.size(80.dp))
                    Text((state as State.Error).errorMsg.toString())
                }
            }

            else -> {
                statePull.endRefresh()
                contentView()
            }
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = statePull,
        )
    }
}