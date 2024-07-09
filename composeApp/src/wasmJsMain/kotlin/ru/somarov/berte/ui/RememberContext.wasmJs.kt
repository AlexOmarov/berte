package ru.somarov.berte.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.browser.document

@Composable
actual fun rememberContext(): Any {
    return remember { document }
}
