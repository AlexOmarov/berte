package ru.somarov.berte.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberContext(): Any {
    return LocalContext.current
}
