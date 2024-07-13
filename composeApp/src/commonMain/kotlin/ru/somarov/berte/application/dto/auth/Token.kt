package ru.somarov.berte.application.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val value: String,
    val expiresIn: Long?,
    val provider: Provider
)
