package ru.somarov.berte.infrastructure.client.request

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.util.UuidSerializer

@Serializable
data class JoinSessionRequest(@Serializable(with = UuidSerializer::class) val sessionId: Uuid)
