package ru.somarov.berte.infrastructure.client.request

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.serialization.UuidSerializer

@Serializable
data class CloseSessionRequest(@Serializable(with = UuidSerializer::class) val sessionId: Uuid)
