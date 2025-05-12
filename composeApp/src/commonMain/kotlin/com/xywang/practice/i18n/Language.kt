package com.xywang.practice.i18n

import androidx.compose.ui.text.intl.Locale

/**
 * 应用支持的语言类型密封类
 *
 * 每个语言对象包含：
 * - ISO格式语言标签（符合BCP47标准）
 * - 对应的本地化显示名称
 * - 转换到系统Locale的能力
 */
sealed class Language(
    val isoFormat: String,
    val localizedName: String
) {
    data object English : Language(
        isoFormat = "en",
        localizedName = "English"
    )

    data object ChineseSimplified : Language(
        isoFormat = "zh-CN",
        localizedName = "简体中文"
    )

    data object ChineseTraditionalTW : Language(
        isoFormat = "zh-TW",
        localizedName = "繁體中文 (台灣)"
    )

    data object ChineseTraditionalHK : Language(
        isoFormat = "zh-HK",
        localizedName = "繁體中文 (香港)"
    )

    data object Japanese : Language(
        isoFormat = "ja-jp",
        localizedName = "日本語"
    )
    // 扩展方法：转换为Compose的Locale对象
    fun toComposeLocale(): Locale = Locale(isoFormat)

    companion object {
        /**
         * 从ISO格式语言标签反向解析语言类型
         * @throws IllegalArgumentException 当传入不支持的ISO标签时
         */
        fun fromIsoFormat(iso: String): Language {
            return when (iso.lowercase()) {
                "en", "en-us" -> English
                "zh-cn" -> ChineseSimplified
                "zh-tw" -> ChineseTraditionalTW
                "zh-hk" -> ChineseTraditionalHK
                "ja", "ja-jp" -> Japanese
                else -> throw IllegalArgumentException("Unsupported language ISO format: $iso")
            }
        }

        /**
         * 获取系统默认语言（智能匹配到应用支持的语言）
         */
        fun systemDefault(): Language {
            val systemLocale = Locale.current
            return when (systemLocale.language) {
                "zh" -> when (systemLocale.region) {
                    "CN" -> ChineseSimplified
                    "TW" -> ChineseTraditionalTW
                    "HK" -> ChineseTraditionalHK
                    else -> ChineseSimplified // 默认回退到简体中文
                }
                "ja", "ja-jp" -> Japanese
                else -> English  // 默认回退英语
            }
        }

        /**
         * 获取所有支持的语言列表（按字母顺序排序）
         */
        fun supportedLanguages(): List<Language> = listOf(
            English,
            ChineseSimplified,
            ChineseTraditionalTW,
            ChineseTraditionalHK,
            Japanese
        ).sortedBy { it.localizedName }
    }
}
