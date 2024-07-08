package ru.somarov.berte

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import ru.somarov.berte.application.viewmodel.AppViewModel
import ru.somarov.berte.ui.screen.HomeScreen
import ru.somarov.berte.ui.screen.LoginScreen
import ru.somarov.berte.ui.screen.RegisterScreen
import ru.somarov.berte.ui.screen.SessionScreen

@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel = viewModel(key = "app") { AppViewModel(navController) }
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = UIScreen.valueOf(
        backStackEntry?.destination?.route ?: UIScreen.Login.name
    )

    navController.createGraph(
        startDestination = UIScreen.Login.name,
        route = "app"
    ) {

    }
    Scaffold(
        topBar = {
            UIAppBar(
                current = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        modifier = modifier,
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = UIScreen.Login.name,

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            UIScreen.entries
                .forEach { screen ->
                    composable(route = screen.name) {
                        when (screen) {
                            UIScreen.Login -> LoginScreen(navController, viewModel = viewModel)
                            UIScreen.Home -> HomeScreen(navController, viewModel = viewModel)
                            UIScreen.Register -> RegisterScreen(navController, viewModel = viewModel)
                            UIScreen.Session -> SessionScreen(navController)
                        }
                    }
                }
        }
    }
}

data class BrScreen(val id: String, val title: String, val icon: ImageVector, val root: Boolean)
enum class UIScreen(val screen: BrScreen) {
    Login(BrScreen("login", "Login", Icons.Outlined.Home, true)),
    Home(BrScreen("home", "Home", Icons.Outlined.Home, true)),
    Register(BrScreen("register", "Register", Icons.Outlined.Home, false)),
    Session(BrScreen("session", "Session", Icons.Outlined.Home, false)),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIAppBar(
    current: UIScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(current.screen.title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}
