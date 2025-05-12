package com.xywang.practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import com.xywang.practice.i18n.Language
import com.xywang.practice.i18n.changeLanguage
import composepractice.composeapp.generated.resources.Res
import composepractice.composeapp.generated.resources.compose_multiplatform
import composepractice.composeapp.generated.resources.greetings
import composepractice.composeapp.generated.resources.switch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var lang by remember { mutableStateOf(Language.systemDefault().isoFormat) }

    AppEnvironment(
        language = lang
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    lang = switchLanguage(lang)
                    changeLanguage(lang)
                }
            ) {
                Text(stringResource(Res.string.switch))
            }
            Text("Current Language: $lang")
            Image(painterResource(Res.drawable.compose_multiplatform), null)
            Text(stringResource(Res.string.greetings))
        }
    }
}
private fun switchLanguage(lang: String) : String{
    return when (lang) {
        Language.English.isoFormat -> Language.ChineseSimplified.isoFormat
        Language.ChineseSimplified.isoFormat -> Language.ChineseTraditionalTW.isoFormat
        Language.ChineseTraditionalTW.isoFormat -> Language.ChineseTraditionalHK.isoFormat
        Language.ChineseTraditionalHK.isoFormat -> Language.Japanese.isoFormat
        else -> Language.English.isoFormat
    }
}