package ru.somarov.berte.infrastructure.client.backend.response

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.backend.response.dto.Session

@Serializable
data class GetSessionsResponse(val sessions: List<Session>)
