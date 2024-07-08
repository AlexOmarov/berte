package ru.somarov.berte.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.somarov.berte.UIScreen
import ru.somarov.berte.application.viewmodel.AppViewModel
import ru.somarov.berte.application.viewmodel.HomeScreenViewModel
import ru.somarov.berte.ui.Messages

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(key = "app") { AppViewModel(navController) },
    screenViewModel: HomeScreenViewModel = viewModel { HomeScreenViewModel() }
) {
    val messages by screenViewModel.messages.collectAsState()
    val sorted = remember(messages) { messages.sortedByDescending { it.date } }

    var value by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Row {
            Button(onClick = { viewModel.navigateTo(UIScreen.Session) }) {
                Text("Session")
            }
            Button(onClick = { viewModel.navigateTo(UIScreen.Login) }) {
                Text("Logout")
            }
        }

        Messages(messages = sorted)

        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Message") },
            trailingIcon = {
                IconButton(onClick = {
                    scope.launch {
                        screenViewModel.sendMessage(value)
                        value = ""
                    }
                }) {
                    Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = "Send")
                }
            }
        )
    }
}
