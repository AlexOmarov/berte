package ru.somarov.berte.infrastructure.navigation

import androidx.navigation.NavController
import ru.somarov.berte.ui.Route

fun NavController.navigateTo(route: Route) {
    if (route.info.root) {
        popBackStack()
    }
    navigate(route.name)
}
