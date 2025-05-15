package com.xywang.practice.i18n

import androidx.compose.runtime.staticCompositionLocalOf

val LocalLocalization = staticCompositionLocalOf { Language.English.isoFormat }
