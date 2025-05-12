package com.xywang.practice

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.xywang.practice.i18n.Language

@Composable
fun AppEnvironment(
    language: String = Language.systemDefault().isoFormat,
    theme: MaterialTheme = MaterialTheme,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalLocalization provides language,
//        LocalTheme provides theme, // 在这里添加你的主题
        // LocalNavigation provides myNavController, // 如果你有导航相关的 Local
        // LocalAnalyticsService provides myAnalytics, // 如果你有分析服务相关的 Local
        content = content
    )
}