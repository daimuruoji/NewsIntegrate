package com.liyan.news.view

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.graphics.LinearGradient
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Author : sun
 * Date: 2024/11/19 10:48
 * Description:
 */
@Composable
fun CycleProgress(modifier: Modifier = Modifier, state: Boolean, done: (() -> Unit)?) {
    val context = LocalContext.current
    val strokeWidth = 1.dp
    val height = 15.dp
    val progress = 1f

    // 使用动画来更新进度
    var animatedValue by remember { mutableStateOf(0f) }

    // 动画逻辑
    LaunchedEffect(state) {
        ValueAnimator.ofFloat(animatedValue, progress).apply {
            duration = 1000
            addUpdateListener {
                animatedValue = it.animatedValue as Float
                if (animatedValue > 0.5f && !state) {
                    pause()
                }
            }
            addListener(object : AnimatorListener {
                override fun onAnimationStart(p0: Animator) {

                }

                override fun onAnimationEnd(p0: Animator) {
                    done?.invoke()
                }

                override fun onAnimationCancel(p0: Animator) {
                }

                override fun onAnimationRepeat(p0: Animator) {

                }
            })
            if (state) {
                resume()
            }
        }.start()
    }


    Canvas(modifier = modifier.height(height)) {
        val width = size.width
        // 背景
        drawRoundRect(
            color = Color(0xFFEFEFEF),
            size = size.copy(height = height.toPx()),
            cornerRadius = CornerRadius(height.toPx() / 2)
        )

        // 进度条
        val progressWidth = animatedValue * width
        val shader = LinearGradient(
            0f, 0f, progressWidth, height.toPx(),
            Color(0xFF43CBFF).toArgb(),
            Color(0xFFF657E5).toArgb(),
            android.graphics.Shader.TileMode.CLAMP
        )

        // 画进度条
        drawRoundRect(
            brush = ShaderBrush(shader),
            size = size.copy(width = progressWidth, height = height.toPx()),
            cornerRadius = CornerRadius(height.toPx() / 2),
            //style = Stroke(width = strokeWidth.toPx())
        )
        //进度条边框
        //drawRoundRect(
        //    brush = ShaderBrush(shader),
        //    size = size.copy(width = progressWidth, height = height.toPx()),
        //    cornerRadius = CornerRadius(height.toPx() / 2),
        //    style = Stroke(width = strokeWidth.toPx())
        //)
    }
}