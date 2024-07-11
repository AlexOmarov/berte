package ru.somarov.berte.infrastructure.client.request

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.request.dto.Setting

@Serializable
data class CreateSessionRequest(val name: String, val description: String, val setting: Setting)
