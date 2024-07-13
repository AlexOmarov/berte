package ru.somarov.berte.infrastructure.oauth

import kotlinx.serialization.Serializable
import ru.somarov.berte.application.dto.auth.Provider

@Serializable
data class OAuthSettings(
    val authorizationEndpoint: String,
    val provider: Provider,
    val tokenEndpoint: String,
    val clientId: String,
    val redirectUri: String,
    val scope: String?,
    val tokenToService: String?
)
