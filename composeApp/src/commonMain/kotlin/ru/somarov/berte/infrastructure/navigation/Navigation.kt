package ru.somarov.berte.infrastructure.navigation

import androidx.navigation.NavController
import ru.somarov.berte.ui.Screen

object Navigation {
    fun to(screen: Screen, controller: NavController) {
        if (screen.info.root) {
            controller.popBackStack()
        }
        controller.navigate(screen.name)
    }
}
