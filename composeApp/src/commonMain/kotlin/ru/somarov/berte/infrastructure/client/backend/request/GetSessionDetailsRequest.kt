package ru.somarov.berte.infrastructure.client.backend.request

import kotlinx.serialization.Serializable

@Serializable
data class GetSessionDetailsRequest(val id: String)
