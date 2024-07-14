package ru.somarov.berte.infrastructure.client.backend.response.dto

import kotlinx.serialization.Serializable

@Serializable
data class SessionDetails(
    val session: ru.somarov.berte.infrastructure.client.backend.response.dto.Session,
    val players: List<ru.somarov.berte.infrastructure.client.backend.response.dto.Player>
)
