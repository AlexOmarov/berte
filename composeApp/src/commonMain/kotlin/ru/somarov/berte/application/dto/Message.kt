package ru.somarov.berte.application.dto

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

enum class MessageType { InMessage, OutMessage, Warning }

data class Message(
    val text: String,
    val messageType: MessageType,
    val date: Instant = Clock.System.now()
)
