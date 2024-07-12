package ru.somarov.berte.infrastructure.navigation

import androidx.navigation.NavController
import ru.somarov.berte.ui.Screen

fun NavController.navigateTo(screen: Screen) {
    if (screen.info.root) {
        popBackStack()
    }
    navigate(screen.name)
}
