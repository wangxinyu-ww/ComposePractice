package com.xywang.practice.extensions

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.min

/**
 *  仿抖音加载动画
 * @param modifier Modifier
 * @param color Color
 * @param strokeWidth Float
 * @param animationDuration Int
 */
@Composable
fun VideoLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    strokeWidth: Float = 2.5f,
    animationDuration: Int = 1100
) {
    // 使用remember缓存不变的参数
    val animColor = remember { color }
    val density = LocalDensity.current
    val strokeWidthPx = with(density) { remember { strokeWidth.dp.toPx() } }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        // 获取容器最大宽度（转换为像素）
        val maxWidthPx = with(density) { maxWidth.toPx() }
        val infiniteTransition = rememberInfiniteTransition()

        // 预计算常量
        val maxWidthPx18 = remember(maxWidthPx) { maxWidthPx * 1.8f }

        // 动画进度值
        val lineProgress by infiniteTransition.animateFloat(
            initialValue = 50f,
            targetValue = maxWidthPx18,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    50f at 0
                    maxWidthPx at (animationDuration * 0.6).toInt()
                    maxWidthPx18 at animationDuration
                },
                repeatMode = RepeatMode.Restart
            )
        )

        // 使用derivedStateOf优化计算
        val animationParams by remember(lineProgress, maxWidthPx) {
            derivedStateOf { calculateAnimationParams(lineProgress, maxWidthPx) }
        }

        // 绘制动画线条
        Canvas(modifier = Modifier.fillMaxWidth()) {
            val centerX = size.width / 2
            drawLine(
                color = animColor.copy(alpha = animationParams.alpha),
                start = Offset(centerX - animationParams.lineWidth, 0f),
                end = Offset(centerX + animationParams.lineWidth, 0f),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
        }
    }
}


// 提取计算逻辑到单独的函数
private fun calculateAnimationParams(progress: Float, maxWidth: Float): AnimationParams {
    val normalizedProgress = progress / maxWidth

    // 使用coerceIn确保值在有效范围内
    val alpha = when {
        normalizedProgress <= 1f -> normalizedProgress
        else -> ((1.8f - normalizedProgress) / 0.8f).coerceIn(0f, 1f)
    }

    val lineWidth = (maxWidth / 2) * min(normalizedProgress, 1f)

    return AnimationParams(alpha, lineWidth)
}

// 创建数据类来存储动画参数
private data class AnimationParams(
    val alpha: Float,
    val lineWidth: Float
)