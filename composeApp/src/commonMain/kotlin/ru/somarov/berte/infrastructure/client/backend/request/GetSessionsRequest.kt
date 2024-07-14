package ru.somarov.berte.infrastructure.client.backend.request

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.backend.request.dto.SessionStatus

@Serializable
data class GetSessionsRequest(val status: SessionStatus?)
