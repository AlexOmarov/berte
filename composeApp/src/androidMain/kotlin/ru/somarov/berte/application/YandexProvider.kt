package ru.somarov.berte.application

import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthResult.Success
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.internal.strategy.LoginType
import ru.somarov.berte.application.dto.auth.Token
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.ui.OAuthActivity

class YandexProvider : OIDAndroidProvider<YandexAuthResult, YandexAuthLoginOptions> {
    override fun formAuthProcess(
        store: TokenStore,
        settings: OAuthSettings,
        activity: OAuthActivity
    ): OAuthProcess<YandexAuthResult, YandexAuthLoginOptions> {
        val loginOptions = YandexAuthLoginOptions(loginType = LoginType.NATIVE)
        val sdk = YandexAuthSdk.create(YandexAuthOptions(activity.applicationContext, false))

        return OAuthProcess(
            { result -> store.set(formToken(result)) },
            sdk.contract,
            loginOptions
        )
    }

    override fun getProvider(): Token.TokenProvider {
        return Token.TokenProvider.YANDEX
    }

    private fun formToken(result: YandexAuthResult): Token? {
        return if (result is Success) {
            Token(result.token.value, result.token.expiresIn, getProvider())
        } else {
            null
        }
    }
}
