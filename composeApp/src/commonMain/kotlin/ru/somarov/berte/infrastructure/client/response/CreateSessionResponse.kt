package ru.somarov.berte.infrastructure.client.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionResponse(val id: String)
