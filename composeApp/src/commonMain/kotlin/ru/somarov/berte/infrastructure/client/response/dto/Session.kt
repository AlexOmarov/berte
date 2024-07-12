package ru.somarov.berte.infrastructure.client.response.dto

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.request.dto.Setting
import ru.somarov.berte.infrastructure.uuid.UUID
import ru.somarov.berte.infrastructure.uuid.UUIDSerializer

@Serializable
data class Session(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val description: String,
    val setting: Setting
)
