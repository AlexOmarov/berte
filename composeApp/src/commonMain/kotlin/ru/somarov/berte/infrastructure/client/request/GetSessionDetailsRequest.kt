package ru.somarov.berte.infrastructure.client.request

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.uuid.UUID
import ru.somarov.berte.infrastructure.uuid.UUIDSerializer

@Serializable
data class GetSessionDetailsRequest(@Serializable(with = UUIDSerializer::class) val id: UUID)
