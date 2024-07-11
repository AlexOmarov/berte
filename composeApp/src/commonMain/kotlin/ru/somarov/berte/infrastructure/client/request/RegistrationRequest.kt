package ru.somarov.berte.infrastructure.client.request

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(val login: String, val password: String)
