package ru.somarov.berte.infrastructure.oauth

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.somarov.berte.application.dto.auth.Token

class TokenStore {
    private val _tokenFlow = MutableSharedFlow<Token?>()
    val tokenFlow = _tokenFlow.asSharedFlow()

    suspend fun set(token: Token?) = _tokenFlow.emit(token)
}
