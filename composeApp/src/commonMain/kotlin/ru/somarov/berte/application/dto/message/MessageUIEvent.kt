package ru.somarov.berte.application.dto.message

import kotlinx.serialization.Serializable

@Serializable
data class MessageUIEvent(
    val animation: MessageAnimation? = null,
    val sound: String?
)
