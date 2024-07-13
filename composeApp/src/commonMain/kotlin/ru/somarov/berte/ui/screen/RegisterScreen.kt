package ru.somarov.berte.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.somarov.berte.infrastructure.navigation.navigateTo
import ru.somarov.berte.ui.Route

@Composable
fun RegisterScreen(controller: NavController) {
    Column {
        Button(onClick = { controller.navigateTo(Route.Home) }) {
            Text("Home")
        }
    }
}
