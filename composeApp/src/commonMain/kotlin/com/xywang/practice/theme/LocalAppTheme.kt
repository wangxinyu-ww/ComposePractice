package com.xywang.practice.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class AppThemeColors(val primary: String, val background: String)
val LocalAppThemeColors = staticCompositionLocalOf {
    AppThemeColors(primary = "#6200EE", background = "#FFFFFF") // 默认主题颜色
}