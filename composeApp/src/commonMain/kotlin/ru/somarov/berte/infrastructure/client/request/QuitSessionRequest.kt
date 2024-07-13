package ru.somarov.berte.infrastructure.client.request

import kotlinx.serialization.Serializable

@Serializable
data class QuitSessionRequest(val sessionId: String)
