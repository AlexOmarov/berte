package ru.somarov.berte.infrastructure.client.response

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.serialization.UuidSerializer

@Serializable
data class RegistrationResponse(@Serializable(with = UuidSerializer::class) val id: Uuid)
