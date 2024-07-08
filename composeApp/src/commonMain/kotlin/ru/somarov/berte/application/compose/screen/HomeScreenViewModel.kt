package ru.somarov.berte.application.compose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class HomeScreenViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()
    suspend fun sendMessage(text: String, messageType: MessageType = MessageType.OutMessage) {
        val message = Message(text, messageType)
        val m = _messages.value.toMutableList()
        m.add(message)
        _messages.emit(m.toList())
    }
    init {
        messages
            .debounce(2000)
            .onEach {
            if (it.isNotEmpty() && it.last().messageType == MessageType.OutMessage) {
                sendMessage("get message from server", MessageType.InMessage)
            }
        }.launchIn(viewModelScope)
    }
}

enum class MessageType { InMessage, OutMessage, Warning }
data class Message(val text: String, val messageType: MessageType, val date: Instant = Clock.System.now())

@Composable
fun ColumnScope.UIMessages(messages: List<Message>, modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = modifier.weight(1.0f),
        reverseLayout = true,
    ) {
        messages.forEach {
            item {
                UIMessage(message = it) {
                    // TODO: open message details
                }
            }
        }
    }
}

@Composable
fun UIMessage(message: Message, modifier: Modifier = Modifier, onClick: () -> Unit) {
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
            textAlign  = when  (message.messageType)  {
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