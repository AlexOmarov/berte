package ru.somarov.berte.infrastructure.oauth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthSettings(
    val authorizationEndpoint: String,
    val tokenEndpoint: String,
    val clientId: String,
    val redirectUri: String,
    val scope: String?,
    val tokenToService: String?
)
