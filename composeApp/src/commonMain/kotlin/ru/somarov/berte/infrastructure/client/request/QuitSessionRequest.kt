package ru.somarov.berte.infrastructure.client.request

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.util.UuidSerializer

@Serializable
data class QuitSessionRequest(@Serializable(with = UuidSerializer::class) val sessionId: Uuid)
