package ru.somarov.berte.infrastructure.client.response

import kotlinx.serialization.Serializable
import ru.somarov.berte.infrastructure.client.response.dto.SessionDetails

@Serializable
data class GetSessionDetailsResponse(val details: SessionDetails)
