package ru.somarov.berte.infrastructure.client.backend.request

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val message: String,
    val sessionId: String
)
