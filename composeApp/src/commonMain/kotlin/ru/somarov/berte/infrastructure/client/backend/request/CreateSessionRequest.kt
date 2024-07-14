package ru.somarov.berte.infrastructure.client.backend.request

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.backend.request.dto.Setting

@Serializable
data class CreateSessionRequest(val name: String, val description: String, val setting: Setting)
