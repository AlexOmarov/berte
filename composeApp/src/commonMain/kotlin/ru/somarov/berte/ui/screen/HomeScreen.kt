package ru.somarov.berte.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
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
import kotlinx.coroutines.launch
import ru.somarov.berte.application.viewmodel.AuthViewModel
import ru.somarov.berte.application.viewmodel.HomeScreenViewModel
import ru.somarov.berte.ui.element.Message

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    screenViewModel: HomeScreenViewModel = viewModel { HomeScreenViewModel() }
) {
    val messages by screenViewModel.messages.collectAsState()
    val sorted = remember(messages) { messages.sortedByDescending { it.date } }

    var value by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        LazyColumn(
            modifier = modifier.weight(1.0f),
            reverseLayout = true,
        ) {
            sorted.forEach {
                item {
                    Message(message = it) {
                        // TODO: open message details
                    }
                }
            }
        }

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
