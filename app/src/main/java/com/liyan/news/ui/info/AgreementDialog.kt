package com.liyan.news.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

/**
 * Author : sun
 * Date: 2024/11/19 14:37
 * Description:
 */
@Composable
fun AgreementDialog(showPopup: Boolean, onDismiss: () -> Unit, onAgree: () -> Unit) {
    if (showPopup) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // 设置透明度
        ) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = {},
                properties = PopupProperties(true),
            ) {
                PrivacyPolicyDialog(onDismiss, onAgree)
            }
        }

    }
}

@Composable
fun PrivacyPolicyDialog(onDismiss: () -> Unit, onAgree: () -> Unit) {
    // 外部容器
    Box(
        modifier = Modifier
            .width(340.dp)
            .padding(12.dp)
            .background(Color(0xFFFAECCE), shape = RoundedCornerShape(8.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp, 0.dp)
        ) {
            // 标题
            Text(
                text = "服务协议及隐私政策",
                color = Color(0xFFDAA520), // 使用相应的 color 资源
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(12.dp)
            )

            // 内容区域
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)) // 使用相应的背景 drawable
                    .padding(10.dp)
            ) {
                Text(
                    text = "我们根据最新的法律，向您说明本软件的隐私政策，特向您推送本提示。请您阅读并充分理解相关条款。",
                    color = Color(0xFFDAA520), // 使用相应的 color 资源
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "为给您带来最好的体验，请允许我们向您申请以下权限:",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "电话权限：获取设备码，保证软件和服务安全",
                    color = Color(0xFFDAA520),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = "存储权限：初始化和存储软件数据",
                    color = Color(0xFFDAA520),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = "我们会严格按照《网络安全法》、《信息网络传播保护条例》等保护您的个人信息。未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径和目的",
                    color = Color(0xFFDAA520),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .background(Color(0xFFFAECCE), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                )
            }

            // 按钮区域
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // 不同意按钮
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(360.dp)),
                    colors = ButtonDefaults.buttonColors(
                        //contentColor = Color(0xFFCD3E2A),
                        containerColor = Color(0xFF808080),
                        //disabledContainerColor = Color(0xFFCD3E2A),
                        //disabledContentColor = Color(0xFFCD3E2A)
                    )
                ) {
                    Text(text = "不同意", color = Color.White)
                }
                // 同意按钮
                Button(
                    onClick = onAgree,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(360.dp)),
                    colors = ButtonDefaults.buttonColors(
                        //contentColor = Color(0xFFCD3E2A),
                        containerColor = Color(0xFFCD3E2A),
                        //disabledContainerColor = Color(0xFFCD3E2A),
                        //disabledContentColor = Color(0xFFCD3E2A)
                    )
                ) {
                    Text(text = "同意", color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPrivacyPolicyDialog() {
    PrivacyPolicyDialog(onDismiss = {}, onAgree = {})
}
