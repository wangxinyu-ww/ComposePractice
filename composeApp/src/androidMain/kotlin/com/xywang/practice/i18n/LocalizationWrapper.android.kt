package com.xywang.practice.i18n

import java.util.Locale

actual fun changeLanguage(lang: String) {
    val locale = when {
        // Handle complex language tags with region
        lang.contains("-") -> {
            val parts = lang.split("-", limit = 2)
            val language = parts[0]
            val region = parts[1]
            Locale(language, region)
        }
        // Simple language tags
        else -> Locale(lang)
    }
    Locale.setDefault(locale)
}