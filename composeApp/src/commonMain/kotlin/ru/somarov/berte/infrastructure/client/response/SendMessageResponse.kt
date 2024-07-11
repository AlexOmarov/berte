package ru.somarov.berte.infrastructure.client.response

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.util.UuidSerializer

@Serializable
data class SendMessageResponse(@Serializable(with = UuidSerializer::class) val messageId: Uuid)
