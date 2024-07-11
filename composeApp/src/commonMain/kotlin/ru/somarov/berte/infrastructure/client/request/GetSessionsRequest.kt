package ru.somarov.berte.infrastructure.client.request

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.request.dto.SessionStatus

@Serializable
data class GetSessionsRequest(val status: SessionStatus?)
