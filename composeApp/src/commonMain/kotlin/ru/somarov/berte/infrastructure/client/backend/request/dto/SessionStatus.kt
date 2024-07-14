package ru.somarov.berte.infrastructure.client.backend.request.dto

import kotlinx.serialization.Serializable

@Serializable
enum class SessionStatus { ACTIVE, CLOSED }
