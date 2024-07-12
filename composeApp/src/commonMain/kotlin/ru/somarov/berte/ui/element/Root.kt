package ru.somarov.berte.ui.element

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.somarov.berte.application.viewmodel.AuthViewModel
import ru.somarov.berte.infrastructure.uuid.UUID
import ru.somarov.berte.ui.Screen
import ru.somarov.berte.ui.screen.HomeScreen
import ru.somarov.berte.ui.screen.LoginScreen
import ru.somarov.berte.ui.screen.RegisterScreen
import ru.somarov.berte.ui.screen.SessionScreen

@Suppress("CyclomaticComplexMethod")
@Composable
fun Root(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val viewModel = viewModel(key = UUID.generate().toString()) { AuthViewModel(navController) }

    val user by viewModel.authUser.collectAsState()
    var openUser by remember { mutableStateOf(false) }
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.Login.name
    )
    if (openUser) {
        user?.let {
            UserDialog(it) {
                openUser = false
            }
        }
    }
    Scaffold(
        topBar = {
            if (currentScreen.info.fullscreen.not()) {
                TopBar(
                    current = currentScreen,
                    user = user,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    openUser = { openUser = true },
                    logout = { viewModel.logout() },
                    navigateUp = { navController.navigateUp() }
                )
            }
        },
        modifier = modifier,
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.name,

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Screen.entries
                .forEach { screen ->
                    composable(route = screen.name) {
                        when (screen) {
                            Screen.Login -> LoginScreen(viewModel = viewModel, navController)
                            Screen.Home -> HomeScreen(viewModel = viewModel)
                            Screen.Register -> RegisterScreen(controller = navController)
                            Screen.Session -> SessionScreen()
                        }
                    }
                }
        }
    }
}
