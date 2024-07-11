package ru.somarov.berte.infrastructure.client.response.dto

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.request.dto.Setting
import ru.somarov.berte.infrastructure.serialization.UuidSerializer

@Serializable
data class Session(
    @Serializable(with = UuidSerializer::class)
    val id: Uuid,
    val name: String,
    val description: String,
    val setting: Setting
)
