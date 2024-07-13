package ru.somarov.berte.infrastructure.client.request

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val message: String,
    val sessionId: String
)
