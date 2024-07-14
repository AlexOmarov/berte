package ru.somarov.berte.infrastructure.oauth

import kotlinx.serialization.Serializable
import ru.somarov.berte.application.dto.auth.Token

@Serializable
enum class OIDProvider(val settings: OAuthSettings, val tokenProvider: Token.TokenProvider) {
    GOOGLE(
        OAuthSettings(
            authorizationEndpoint = "https://accounts.google.com/o/oauth2/auth",
            tokenEndpoint = "https://oauth2.googleapis.com/token",
            clientId = "191033215032-36m9077g5pqd2l67i686qslb50mtveha.apps.googleusercontent.com",
            redirectUri = "ru.somarov.berte:/oauth2redirect",
            scope = "profile openid"
        ),
        Token.TokenProvider.GOOGLE
    ), YANDEX(
        OAuthSettings(
            authorizationEndpoint = "",
            tokenEndpoint = "",
            clientId = "",
            redirectUri = "",
            scope = ""
        ),
        Token.TokenProvider.YANDEX
    ), APPLE(
        OAuthSettings(
            authorizationEndpoint = "",
            tokenEndpoint = "",
            clientId = "",
            redirectUri = "",
            scope = ""
        ),
        Token.TokenProvider.APPLE
    ), OK(
        OAuthSettings(
            authorizationEndpoint = "",
            tokenEndpoint = "",
            clientId = "",
            redirectUri = "",
            scope = ""
        ),
        Token.TokenProvider.OK
    ), TELEGRAM(
        OAuthSettings(
            authorizationEndpoint = "",
            tokenEndpoint = "",
            clientId = "",
            redirectUri = "",
            scope = ""
        ),
        Token.TokenProvider.TELEGRAM
    ), VK(
        OAuthSettings(
            authorizationEndpoint = "",
            tokenEndpoint = "",
            clientId = "",
            redirectUri = "",
            scope = ""
        ),
        Token.TokenProvider.VK
    )
}
