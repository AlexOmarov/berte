package ru.somarov.berte.infrastructure.oauth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.somarov.berte.application.dto.auth.TokenInfo

class TokenStore {
    private val _tokenFlow = MutableStateFlow<TokenInfo?>(null)
    val tokenFlow = _tokenFlow.asStateFlow()

    suspend fun setToken(token: TokenInfo?) = _tokenFlow.emit(token)
}
