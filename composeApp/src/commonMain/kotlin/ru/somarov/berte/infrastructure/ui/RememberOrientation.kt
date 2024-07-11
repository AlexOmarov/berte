package ru.somarov.berte.infrastructure.ui

import androidx.compose.runtime.Composable
import ru.somarov.berte.ui.Orientation

@Composable
expect fun rememberOrientation(): Orientation
