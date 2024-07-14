package ru.somarov.berte.application

import ru.somarov.berte.application.dto.auth.Token
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.ui.OAuthActivity

interface OIDAndroidProvider {
    fun authenticate(store: TokenStore, settings: OAuthSettings, activity: OAuthActivity)
    fun getProvider(): Token.TokenProvider
}
