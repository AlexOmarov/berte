package ru.somarov.berte.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.somarov.berte.application.dto.Message
import ru.somarov.berte.application.dto.MessageType

@Composable
fun WaitBox(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorBox(message: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Text(message, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun ColumnScope.Messages(messages: List<Message>, modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = modifier.weight(1.0f),
        reverseLayout = true,
    ) {
        messages.forEach {
            item {
                Message(message = it) {
                    // TODO: open message details
                }
            }
        }
    }
}

@Composable
fun Message(message: Message, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier.paddingMessage(message).fillMaxWidth(),
        colors = if (message.messageType == MessageType.Warning)
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        else
            CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {
        Text(
            text = message.text,
            modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
            textAlign = when (message.messageType) {
                MessageType.InMessage -> TextAlign.Left
                MessageType.OutMessage -> TextAlign.Right
                MessageType.Warning -> TextAlign.Center
            }
        )

    }
}

private fun Modifier.paddingMessage(message: Message): Modifier {
    return when (message.messageType) {
        MessageType.InMessage -> padding(end = 28.dp)
        MessageType.OutMessage -> padding(start = 28.dp)
        MessageType.Warning -> this
    }
}
