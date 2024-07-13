package ru.somarov.berte.infrastructure.oauth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.somarov.berte.application.dto.auth.Token

class TokenStore {
    private val _tokenFlow = MutableStateFlow<Token?>(null)
    val tokenFlow = _tokenFlow.asStateFlow()

    suspend fun set(token: Token?) = _tokenFlow.emit(token)
}
