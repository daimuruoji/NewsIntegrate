package com.liyan.news.ui.info

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.liyan.news.ui.theme.NewsTheme
import com.liyan.news.view.ProgressWebView
import com.liyan.news.view.TitleBar


class NewsDetailActivity : ComponentActivity() {

    private val mWebView by lazy {
        ProgressWebView(this).apply {
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
    }

    companion object {
        const val TITLE = "title"
        const val CONTENT = "content"

        fun go(context: Context, title: String, content: String) {
            Intent(context, NewsDetailActivity::class.java).also {
                it.putExtra(TITLE, title)
                it.putExtra(CONTENT, content)
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra(TITLE)!!
        val content = intent.getStringExtra(CONTENT)!!
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                NewsDetailPage(title = title, content = content, backClick = {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack()
                    } else {
                        finish()
                    }
                }, webView = mWebView)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private var audioManager: AudioManager? = null
    private var listener: OnAudioFocusChangeListener? = null


    override fun onPause() {
        mWebView.onPause()
        mWebView.pauseTimers()

        audioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager?
        listener = OnAudioFocusChangeListener { }
        val result = audioManager!!.requestAudioFocus(
            listener,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
        )

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        }
        super.onPause()

    }

    override fun onResume() {
        mWebView.onResume()
        mWebView.resumeTimers()
        audioManager?.let {
            it.abandonAudioFocus(listener);
            audioManager = null;
        }
        super.onResume()
    }

    override fun onDestroy() {
        mWebView.destroy()
        super.onDestroy()
    }
}


@Composable
fun NewsDetailPage(title: String, content: String, backClick: () -> Unit, webView: WebView) {
    Column {
        TitleBar(title, showBack = true, backClick = backClick)
        AndroidView(factory = {
            webView
        }) {
            it.loadUrl(content)
        }
    }
}