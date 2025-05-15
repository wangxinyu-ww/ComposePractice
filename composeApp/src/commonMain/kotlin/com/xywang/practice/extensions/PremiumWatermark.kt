package com.xywang.practice.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.toIntSize
import composepractice.composeapp.generated.resources.Res
import composepractice.composeapp.generated.resources.crown
import org.jetbrains.compose.resources.painterResource

/**
 * Applies a premium watermark overlay to a [Modifier]. The watermark includes a scrim, border,
 * and a customizable icon, positioned within the composable.
 *
 * @param isVisible Determines whether the watermark is displayed.
 * @param shape The shape of the scrim and border.
 * @param scrimColor The color of the scrim overlay.
 * @param borderColor The color of the border.
 * @param borderWidth The thickness of the border.
 * @param markColor The tint color for the watermark icon.
 * @param markPainter The painter used to draw the watermark icon.
 * @param markSize The size of the watermark icon. If the size is unspecified, a default size proportional to the container's dimensions will be used.
 * @param markAlignment The alignment of the watermark icon within the composable.
 * @param markPadding The padding applied to the drawable area of the watermark icon.
 * @return A [Modifier] with the premium watermark applied.
 */
@Composable
fun Modifier.premiumWatermark(
    isVisible: Boolean = true,
    shape: Shape = RectangleShape,
    scrimColor: Color = Color.Yellow.copy(0.5f),
    borderColor: Color = Color.Yellow,
    borderWidth: Dp = 1.dp,
    markColor: Color = Color.Yellow,
    markPainter: Painter = painterResource(Res.drawable.crown),
    markSize: DpSize = DpSize.Unspecified,
    markAlignment: Alignment = Alignment.TopEnd,
    markPadding: PaddingValues = PaddingValues()
): Modifier {
    return if (isVisible) {
        this.premiumWatermark(
            shape = shape,
            scrimColor = scrimColor,
            borderColor = borderColor,
            borderWidth = borderWidth,
            markColor = markColor,
            markPainter = markPainter,
            markSize = markSize,
            markAlignment = markAlignment,
            markPadding = markPadding
        )
    } else {
        this
    }
}

/**
 * Applies a premium watermark overlay to a [Modifier]. The watermark includes a scrim, border,
 * and a customizable icon, positioned within the composable.
 *
 * @param shape The shape of the scrim and border.
 * @param scrimColor The color of the scrim overlay.
 * @param borderColor The color of the border.
 * @param borderWidth The thickness of the border.
 * @param markColor The tint color for the watermark icon.
 * @param markPainter The painter used to draw the watermark icon.
 * @param markSize The size of the watermark icon. If the size is unspecified, a default size proportional to the container's dimensions will be used.
 * @param markAlignment The alignment of the watermark icon within the composable.
 * @param markPadding The padding applied to the drawable area of the watermark icon.
 * @return A [Modifier] with the premium watermark applied.
 */
@Composable
fun Modifier.premiumWatermark(
    shape: Shape = RectangleShape,
    scrimColor: Color = Color.Yellow.copy(0.5f),
    borderColor: Color = Color.Yellow,
    borderWidth: Dp = 1.dp,
    markColor: Color = Color.Yellow,
    markPainter: Painter = painterResource(Res.drawable.crown),
    markSize: DpSize = DpSize.Unspecified,
    markAlignment: Alignment = Alignment.TopEnd,
    markPadding: PaddingValues = PaddingValues()
): Modifier {
    return this.drawWithContent {
        drawContent()

        val outline = shape.createOutline(size, layoutDirection, this)

        drawOutline(
            outline = outline,
            color = scrimColor
        )

        drawOutline(
            outline = outline,
            color = borderColor,
            style = Stroke(borderWidth.toPx())
        )

        val markSizePx = if (markSize.isUnspecified) {
            Size(size.minDimension * 0.25f, size.minDimension * 0.25f)
        } else {
            markSize.toSize()
        }

        val leftPadding = markPadding.calculateLeftPadding(layoutDirection).toPx()
        val rightPadding = markPadding.calculateRightPadding(layoutDirection).toPx()
        val topPadding = markPadding.calculateTopPadding().toPx()
        val bottomPadding = markPadding.calculateBottomPadding().toPx()

        val sizeWithPadding = Size(
            width = size.width - (leftPadding + rightPadding),
            height = size.height - (topPadding + bottomPadding)
        )

        val markPosition = markAlignment.align(
            size = markSizePx.toIntSize(),
            space = sizeWithPadding.toIntSize(),
            layoutDirection = layoutDirection
        )

        translate(
            left = leftPadding + markPosition.x,
            top = topPadding + markPosition.y
        ) {
            with(markPainter) {
                draw(
                    size = markSizePx,
                    colorFilter = ColorFilter.tint(markColor)
                )
            }
        }
    }
}


@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp) // 外边距
            .background(Color.White)
            .padding(16.dp), //内边距
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Checkbox(
            checked = true,
            onCheckedChange = {},
            modifier = Modifier
                .premiumWatermark(
                    shape = RoundedCornerShape(2.dp),
                    markAlignment = Alignment.TopStart,
                    markPadding = PaddingValues(2.dp),
                    markSize = DpSize(8.dp, 8.dp)
                )
                .size(20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Switch(
            checked = false,
            onCheckedChange = {},
            modifier = Modifier
                .premiumWatermark(
                    shape = CircleShape,
                    markAlignment = Alignment.CenterStart,
                    markPadding = PaddingValues(start = 10.dp),
                    markSize = DpSize(12.dp, 12.dp)
                )
                .height(32.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        FloatingActionButton(
            onClick = { },
            modifier = Modifier.premiumWatermark(
                shape = FloatingActionButtonDefaults.shape,
                markPadding = PaddingValues(5.dp)
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.crown),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .defaultMinSize(48.dp, 48.dp)
                .premiumWatermark(
                    shape = CircleShape,
                    markPainter = painterResource(Res.drawable.crown),
                    markAlignment = Alignment.CenterEnd,
                    markPadding = PaddingValues(end = 8.dp),
                    markSize = DpSize(18.dp, 18.dp)
                )
        ) {
            Text("Secret Action!")
        }
    }

}