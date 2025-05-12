package com.xywang.practice

import androidx.compose.runtime.staticCompositionLocalOf
import com.xywang.practice.i18n.Language

val LocalLocalization = staticCompositionLocalOf { Language.English.isoFormat }
