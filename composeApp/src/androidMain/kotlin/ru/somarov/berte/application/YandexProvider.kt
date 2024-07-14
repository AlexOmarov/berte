package ru.somarov.berte.application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthResult.Success
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.internal.strategy.LoginType
import kotlinx.coroutines.launch
import ru.somarov.berte.application.dto.auth.Token
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.ui.OAuthActivity

class YandexProvider : OIDAndroidProvider {
    override fun authenticate(store: TokenStore, settings: OAuthSettings, activity: OAuthActivity) {
        check(activity.lifecycle.currentState == Lifecycle.State.CREATED) {
            "Cannot process oauth authentication using activity in states other than created." +
                "Current: ${activity.lifecycle.currentState}"
        }
        val loginOptions = YandexAuthLoginOptions(loginType = LoginType.NATIVE)
        val sdk = YandexAuthSdk.create(YandexAuthOptions(activity.applicationContext, false))
        val yandexLauncher =
            activity.registerForActivityResult(sdk.contract) {
                activity.lifecycleScope.launch { store.set(formToken(it)) }
            }
        yandexLauncher.launch(loginOptions)
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
