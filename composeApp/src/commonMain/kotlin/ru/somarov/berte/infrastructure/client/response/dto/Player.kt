package ru.somarov.berte.infrastructure.client.response.dto

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val login: String
)
