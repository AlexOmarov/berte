package ru.somarov.berte.infrastructure.client.request.dto

import kotlinx.serialization.Serializable

@Serializable
enum class SessionStatus { ACTIVE, CLOSED }
