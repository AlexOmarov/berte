package ru.somarov.berte.infrastructure.client.backend.response

import kotlinx.serialization.Serializable

@Serializable
data class JoinSessionResponse(val joined: Boolean)
