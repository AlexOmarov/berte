package ru.somarov.berte.application.compose.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ru.somarov.berte.application.compose.AppViewModel
import ru.somarov.berte.application.compose.UIScreen

@Composable
fun ColdScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(key = "app") {
        AppViewModel(
            navController
        )
    },
    screenViewModel: LoginScreenViewModel = viewModel() {
        LoginScreenViewModel()
    }
) {
    LazyColumn(modifier.fillMaxSize()) {
        screenViewModel.items.forEach {
            item {
                Button(onClick = { viewModel.navigateTo(UIScreen.Register) }) {
                    Text("Register $it")
                }
            }
        }
    }
}

class LoginScreenViewModel : ViewModel() {
    val items = (0..100).map { "Item $it" }
}

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(key = "app") {
        AppViewModel(
            navController
        )
    },
) {
    Column {
        Button(onClick = { viewModel.navigateTo(UIScreen.Session) }) {
            Text("Session")
        }
        Button(onClick = { viewModel.navigateTo(UIScreen.Login) }) {
            Text("Logout")
        }
        Text("Home")
    }
}

@Composable
fun RegisterScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(key = "app") {
        AppViewModel(
            navController
        )
    },
) {
    Column {
        Button(onClick = { viewModel.navigateTo(UIScreen.Home) }) {
            Text("Home")
        }
        Text("Register")
    }
}

@Composable
fun SessionScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Text("Session")
}
