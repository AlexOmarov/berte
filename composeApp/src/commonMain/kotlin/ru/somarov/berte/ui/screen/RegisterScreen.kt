package ru.somarov.berte.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ru.somarov.berte.UIScreen
import ru.somarov.berte.application.viewmodel.AppViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(key = "app") { AppViewModel(navController) },
) {
    Column {
        Button(onClick = { viewModel.navigateTo(UIScreen.Home) }) {
            Text("Home")
        }
        Text("Register")
    }
}
