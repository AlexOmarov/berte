package ru.somarov.berte.infrastructure.client.response

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.uuid.UUID
import ru.somarov.berte.infrastructure.uuid.UUIDSerializer

@Serializable
data class RegistrationResponse(@Serializable(with = UUIDSerializer::class) val id: UUID)
