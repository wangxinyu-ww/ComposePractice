package com.xywang.practice.i18n

import platform.Foundation.NSUserDefaults

actual fun changeLanguage(lang: String) {
    NSUserDefaults.standardUserDefaults.setObject(arrayListOf(lang),"AppleLanguages")
    // Force the settings to synchronize
    NSUserDefaults.standardUserDefaults.synchronize()
}