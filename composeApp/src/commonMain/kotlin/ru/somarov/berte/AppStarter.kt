package ru.somarov.berte

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppStarter() {
    MaterialTheme {
        Box(Modifier.safeDrawingPadding()) { App() }
    }
}
