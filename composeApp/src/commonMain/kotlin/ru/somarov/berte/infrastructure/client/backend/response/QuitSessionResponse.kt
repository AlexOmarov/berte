package ru.somarov.berte.infrastructure.client.backend.response

import kotlinx.serialization.Serializable

@Serializable
data class QuitSessionResponse(val done: Boolean)
