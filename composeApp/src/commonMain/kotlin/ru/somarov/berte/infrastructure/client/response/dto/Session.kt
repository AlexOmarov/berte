package ru.somarov.berte.infrastructure.client.response.dto

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.request.dto.Setting

@Serializable
data class Session(
    val id: String,
    val name: String,
    val description: String,
    val setting: Setting
)
