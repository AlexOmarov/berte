package ru.somarov.berte.infrastructure.client.backend.request

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(val login: String, val password: String)
