package ru.somarov.berte.infrastructure.oauth

import kotlinx.serialization.Serializable
import ru.somarov.berte.application.dto.auth.TokenProvider

@Serializable
data class OAuthSettings(
    val authorizationEndpoint: String,
    val provider: TokenProvider,
    val tokenEndpoint: String,
    val clientId: String,
    val redirectUri: String,
    val scope: String?,
    val tokenToService: String?
)
