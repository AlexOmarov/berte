package ru.somarov.berte.application.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    val value: String,
    val expiresIn: Long?,
    val provider: TokenProvider
)
