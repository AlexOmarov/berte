package ru.somarov.berte.application.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthUser(
    val id: String,
    val username: String,
    val email: List<String>,
    val tokenInfo: TokenInfo,
)
