package ru.somarov.berte.infrastructure.ui

import androidx.compose.runtime.Composable
import ru.somarov.berte.ui.Platform

@Composable
actual fun rememberPlatform(): Platform {
    return Platform.WEB
}
