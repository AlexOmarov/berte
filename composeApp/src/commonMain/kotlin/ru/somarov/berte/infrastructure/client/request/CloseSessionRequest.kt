package ru.somarov.berte.infrastructure.client.request

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.uuid.UUID
import ru.somarov.berte.infrastructure.uuid.UUIDSerializer

@Serializable
data class CloseSessionRequest(@Serializable(with = UUIDSerializer::class) val sessionId: UUID)
