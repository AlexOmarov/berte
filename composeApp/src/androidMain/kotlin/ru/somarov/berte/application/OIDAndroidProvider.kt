package ru.somarov.berte.application

import ru.somarov.berte.application.dto.auth.Token
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.ui.OAuthActivity

interface OIDAndroidProvider<T, V> {
    fun formAuthProcess(
        store: TokenStore,
        settings: OAuthSettings,
        activity: OAuthActivity
    ): OAuthProcess<T, V>

    fun getProvider(): Token.TokenProvider
}
