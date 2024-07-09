package ru.somarov.berte.ui

import androidx.compose.runtime.Composable

enum class Orientation {PORTRAIT, LANDSCAPE}

@Composable
expect fun rememberOrientation(): Orientation