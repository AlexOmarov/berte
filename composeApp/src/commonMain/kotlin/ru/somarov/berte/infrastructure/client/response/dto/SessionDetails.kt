package ru.somarov.berte.infrastructure.client.response.dto

import kotlinx.serialization.Serializable

@Serializable
data class SessionDetails(
    val session: Session,
    val players: List<Player>
)
