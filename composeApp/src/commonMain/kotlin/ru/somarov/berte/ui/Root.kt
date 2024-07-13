package ru.somarov.berte.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.AccountCircle
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.somarov.berte.application.dto.auth.User
import ru.somarov.berte.application.viewmodel.AuthViewModel
import ru.somarov.berte.infrastructure.uuid.createUniqueString
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
    val viewModel = viewModel(key = createUniqueString()) { AuthViewModel(navController) }

    val user by viewModel.user.collectAsState()
    var openUser by remember { mutableStateOf(false) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val route = Route.valueOf(currentRoute ?: Route.Login.name)
    if (openUser) {
        user?.let {
            UserDialog(it) {
                openUser = false
            }
        }
    }
    Scaffold(
        topBar = {
            if (route.info.fullscreen.not()) {
                TopBar(
                    current = route,
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
            startDestination = Route.Login.name,

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Route.entries
                .forEach { screen ->
                    composable(route = screen.name) {
                        when (screen) {
                            Route.Login -> LoginScreen(viewModel = viewModel, navController)
                            Route.Home -> HomeScreen(viewModel = viewModel)
                            Route.Register -> RegisterScreen(controller = navController)
                            Route.Session -> SessionScreen()
                        }
                    }
                }
        }
    }
}

@Suppress("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    current: Route,
    user: User?,
    canNavigateBack: Boolean,
    openUser: () -> Unit,
    logout: () -> Unit,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(current.info.title)
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
fun UserDialog(user: User, onDismiss: () -> Unit) {
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
