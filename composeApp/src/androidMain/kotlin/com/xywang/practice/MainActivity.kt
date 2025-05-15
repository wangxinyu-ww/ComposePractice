package com.xywang.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xywang.practice.extensions.VideoLoadingIndicator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Composable
fun AppAndroidPreview() {
    App()
}

@Preview(
    name = "抖音式加载动画预览",
    group = "自定义动画",
    showBackground = true,
    widthDp = 360,
    heightDp = 80,
    backgroundColor = 0xFF1A1A1A
)
@Composable
fun LoadingIndicatorPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
    ) {
        VideoLoadingIndicator()
    }
}
