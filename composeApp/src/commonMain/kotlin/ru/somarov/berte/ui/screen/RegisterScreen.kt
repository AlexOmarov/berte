package ru.somarov.berte.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.somarov.berte.infrastructure.navigation.Navigation
import ru.somarov.berte.ui.Screen

@Composable
fun RegisterScreen(
    controller: NavController
) {
    Column {
        Button(onClick = { Navigation.to(Screen.Home, controller) }) {
            Text("Home")
        }
        Text("Register")
    }
}
