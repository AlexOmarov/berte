package ru.somarov.berte.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.somarov.berte.application.viewmodel.AppViewModel

@Composable
fun SessionScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier,
) {
    Text("Session")
}
