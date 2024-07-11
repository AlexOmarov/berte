package ru.somarov.berte.infrastructure.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import ru.somarov.berte.ui.Orientation

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun rememberOrientation(): Orientation {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    return remember(windowInfo, density) {
        val height = with(density) { windowInfo.containerSize.height.toDp() }
        val width = with(density) { windowInfo.containerSize.width.toDp() }
        if (width >= height)
            Orientation.LANDSCAPE
        else
            Orientation.PORTRAIT
    }
}
