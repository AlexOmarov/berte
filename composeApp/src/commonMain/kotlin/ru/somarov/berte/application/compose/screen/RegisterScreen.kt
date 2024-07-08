package ru.somarov.berte.application.compose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ru.somarov.berte.application.compose.AppViewModel
import ru.somarov.berte.application.compose.UIScreen

@Composable
fun RegisterScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = androidx.lifecycle.viewmodel.compose.viewModel(key = "app") {
        AppViewModel(
            navController
        )
    },
) {
    Column {
        Button(onClick = { viewModel.navigateTo(UIScreen.Home) }) {
            Text("Home")
        }
        Text("Register")
    }
}