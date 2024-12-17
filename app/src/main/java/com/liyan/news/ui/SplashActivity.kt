package com.liyan.news.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liyan.news.ui.info.AgreementDialog
import com.liyan.news.ui.info.SplashPage
import com.liyan.news.ui.theme.NewsTheme
import com.liyan.news.utils.PreferencesHelper
import com.liyan.news.viewmodel.SplashViewModel

/**
 * Author : sun
 * Date: 2024/11/19 09:53
 * Description:
 */
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: SplashViewModel = viewModel()
            val showPop by viewModel.showPop.observeAsState(PreferencesHelper.showPop)
            val next by viewModel.next.observeAsState(!showPop)
            NewsTheme {
                AgreementDialog(
                    showPop,
                    onDismiss = {
                        viewModel.showPop.value = false
                        finish()
                    },
                    onAgree = {
                        viewModel.showPop.value = false
                        viewModel.next.value = true
                        PreferencesHelper.showPop = false
                    }
                )
                SplashPage(next)
            }
        }
    }
}
