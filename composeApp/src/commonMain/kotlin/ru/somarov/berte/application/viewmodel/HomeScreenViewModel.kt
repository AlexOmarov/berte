package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.somarov.berte.application.dto.Message
import ru.somarov.berte.application.dto.MessageType

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
        @OptIn(kotlinx.coroutines.FlowPreview::class)
        messages
            .debounce(2000)
            .onEach {
                if (it.isNotEmpty() && it.last().messageType == MessageType.OutMessage) {
                    sendMessage("get message from server", MessageType.InMessage)
                }
            }.launchIn(viewModelScope)
    }
}
