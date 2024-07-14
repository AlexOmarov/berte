package ru.somarov.berte.infrastructure.client.backend.response

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.backend.response.dto.SessionDetails

@Serializable
data class GetSessionDetailsResponse(val details: SessionDetails)
