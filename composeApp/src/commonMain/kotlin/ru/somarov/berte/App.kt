package ru.somarov.berte

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.somarov.berte.application.dto.AuthUser
import ru.somarov.berte.application.viewmodel.AppViewModel
import ru.somarov.berte.infrastructure.util.createUUID
import ru.somarov.berte.ui.screen.HomeScreen
import ru.somarov.berte.ui.screen.LoginScreen
import ru.somarov.berte.ui.screen.RegisterScreen
import ru.somarov.berte.ui.screen.SessionScreen

@Suppress("CyclomaticComplexMethod")
@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val viewModelKey = remember(navController) {
        createUUID()
    }
    val viewModel: AppViewModel = viewModel(key = viewModelKey) {
        AppViewModel(navController)
    }
    val user: AuthUser? by viewModel.authUser.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = UIScreen.valueOf(
        backStackEntry?.destination?.route ?: UIScreen.Login.name
    )
    var openUser by remember { mutableStateOf(false) }
    if (openUser) {
        user?.let {
            UIAuthUserDialog(it) {
                openUser = false
            }
        }
    }
    Scaffold(
        topBar = {
            if (currentScreen.screen.fullscreen.not()) {
                UIAppBar(
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
            startDestination = UIScreen.Login.name,

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            UIScreen.entries
                .forEach { screen ->
                    composable(route = screen.name) {
                        when (screen) {
                            UIScreen.Login -> LoginScreen(viewModel = viewModel)
                            UIScreen.Home -> HomeScreen(viewModel = viewModel)
                            UIScreen.Register -> RegisterScreen(viewModel = viewModel)
                            UIScreen.Session -> SessionScreen(viewModel = viewModel)
                        }
                    }
                }
        }
    }
}

data class BrScreen(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val root: Boolean,
    val fullscreen: Boolean
)

enum class UIScreen(val screen: BrScreen) {
    Login(
        BrScreen(
            id = "login",
            title = "Login",
            icon = Icons.Outlined.Home,
            root = true,
            fullscreen = true
        )
    ),
    Home(
        BrScreen(
            id = "home",
            title = "Home",
            icon = Icons.Outlined.Home,
            root = true,
            fullscreen = false
        )
    ),
    Register(
        BrScreen(
            id = "register",
            title = "Register",
            icon = Icons.Outlined.Home,
            root = false,
            fullscreen = false
        )
    ),
    Session(
        BrScreen(
            id = "session",
            title = "Session",
            icon = Icons.Outlined.Home,
            root = false,
            fullscreen = false
        )
    ),
}

@Suppress("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIAppBar(
    current: UIScreen,
    user: AuthUser?,
    canNavigateBack: Boolean,
    openUser: () -> Unit,
    logout: () -> Unit,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(current.screen.title)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            if (user != null) {
                IconButton(onClick = openUser) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = user.username,
                    )
                }
            }
            IconButton(onClick = logout) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                    contentDescription = "logout",
                )
            }
        },
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

@Composable
fun UIAuthUserDialog(user: AuthUser, onDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier.padding(horizontal = 20.dp),
        shape = MaterialTheme.shapes.small,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        onDismissRequest = onDismiss,
        text = {
            Column {
                Text(user.username)
                HorizontalDivider()
                user.email.forEach {
                    Text(it)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Закрыть")
            }
        })
}
