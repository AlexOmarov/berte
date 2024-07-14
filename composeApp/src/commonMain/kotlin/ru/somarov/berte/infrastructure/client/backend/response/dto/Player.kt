package ru.somarov.berte.infrastructure.client.backend.response.dto

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val login: String
)
