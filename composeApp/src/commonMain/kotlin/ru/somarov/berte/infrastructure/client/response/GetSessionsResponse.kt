package ru.somarov.berte.infrastructure.client.response

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.response.dto.Session

@Serializable
data class GetSessionsResponse(val sessions: List<Session>)
