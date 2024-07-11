package ru.somarov.berte.application.dto.message

import kotlinx.serialization.Serializable

@Serializable
data class MessageSpiritInfo(
    val background: String,
    val tint: String,
    val music: String? = null,
)
