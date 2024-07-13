package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.somarov.berte.application.dto.message.Message
import ru.somarov.berte.application.dto.message.Message.MessageType

class HomeScreenViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    fun sendMessage(text: String, messageType: MessageType = MessageType.RESPONSE) {
        val message = Message(text, messageType)
        val m = _messages.value.toMutableList()
        m.add(message)
        viewModelScope.launch { _messages.emit(m.toList()) }
    }

    init {
        @OptIn(kotlinx.coroutines.FlowPreview::class)
        messages
            .debounce(DEBOUNCE_PERIOD)
            .onEach {
                if (it.isNotEmpty() && it.last().messageType == MessageType.RESPONSE) {
                    sendMessage("get message from server", MessageType.REQUEST)
                }
            }.launchIn(viewModelScope)
    }

    companion object {
        const val DEBOUNCE_PERIOD = 2000L
    }
}
