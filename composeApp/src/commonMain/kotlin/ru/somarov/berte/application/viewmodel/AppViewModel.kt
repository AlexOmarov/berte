package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.somarov.berte.UIScreen

class AppViewModel(
    private val navController: NavHostController
) : ViewModel() {

    fun navigateTo(uiScreen: UIScreen) {
        if (uiScreen.screen.root) {
            navController.currentDestination?.route?.let { navController.clearBackStack(it) }
        }
        navController.navigate(uiScreen.name)
    }

}