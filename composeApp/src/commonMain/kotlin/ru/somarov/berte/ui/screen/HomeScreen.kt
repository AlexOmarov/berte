package ru.somarov.berte.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import ru.somarov.berte.application.dto.message.Message
import ru.somarov.berte.application.dto.message.Message.MessageType
import ru.somarov.berte.application.viewmodel.AuthViewModel
import ru.somarov.berte.application.viewmodel.HomeScreenViewModel
import ru.somarov.berte.infrastructure.datetime.Format

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

@Composable
fun Message(message: Message, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val date = remember(message.date) {
        message.date.toLocalDateTime(TimeZone.currentSystemDefault())
            .format(Format.format)
    }
    Card(
        onClick = onClick,
        modifier = modifier.paddingMessage(message)
            .widthIn(min = 200.dp, max = 400.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = date,
                modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                textAlign = when (message.messageType) {
                    MessageType.REQUEST -> TextAlign.Left
                    MessageType.RESPONSE -> TextAlign.Right
                }
            )

            Text(
                text = message.text,
                modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = when (message.messageType) {
                    MessageType.REQUEST -> TextAlign.Left
                    MessageType.RESPONSE -> TextAlign.Right
                }
            )
        }
    }
}

private fun Modifier.paddingMessage(message: Message): Modifier {
    return when (message.messageType) {
        MessageType.REQUEST -> padding(end = 28.dp)
        MessageType.RESPONSE -> padding(start = 28.dp)
    }
}
