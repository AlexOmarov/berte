package ru.somarov.berte.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.somarov.berte.UIScreen
import ru.somarov.berte.application.viewmodel.AppViewModel

@Composable
fun RegisterScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier,
) {
    Column {
        Button(onClick = { viewModel.navigateTo(UIScreen.Home) }) {
            Text("Home")
        }
        Text("Register")
    }
}
