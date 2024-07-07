package ru.somarov.berte.application.compose

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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.somarov.berte.application.compose.screen.ColdScreen
import ru.somarov.berte.application.compose.screen.HomeScreen
import ru.somarov.berte.application.compose.screen.LoginScreen
import ru.somarov.berte.application.compose.screen.RegisterScreen
import ru.somarov.berte.application.compose.screen.SessionScreen

@Composable
fun UIApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel = viewModel(key = "app") { AppViewModel(navController) }
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = UIScreen.valueOf(
        backStackEntry?.destination?.route ?: UIScreen.Cold.name
    )

    navController.createGraph(
        startDestination = UIScreen.Cold.name,
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
            startDestination = UIScreen.Cold.name,

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            UIScreen.entries
                .forEach { screen ->
                    composable(route = screen.name) {
                        when (screen) {
                            UIScreen.Cold -> ColdScreen(navController)
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

class AppViewModel(private val navController: NavHostController) : ViewModel() {
    fun navigateTo(uiScreen: UIScreen) {
        if (uiScreen.screen.root) {
            @Suppress("ControlFlowWithEmptyBody")
            while (navController.popBackStack()) {

            }
        }
        navController.navigate(uiScreen.name)
    }

    init {
        viewModelScope.launch {
            delay(1000L)
            navigateTo(UIScreen.Login)
        }
    }
}

data class BrScreen(val id: String, val title: String, val icon: ImageVector, val root: Boolean)
enum class UIScreen(val screen: BrScreen) {
    Cold(BrScreen("cold", "Cold", Icons.Outlined.Home, true)),
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
