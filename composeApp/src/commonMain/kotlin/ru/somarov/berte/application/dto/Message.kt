package ru.somarov.berte.application.dto

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

enum class MessageType { InMessage, OutMessage, Warning }

@Serializable
data class Message(
    val text: String,
    val messageType: MessageType,
    val date: Instant = Clock.System.now()
)
