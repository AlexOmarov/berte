package ru.somarov.berte.application

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import io.ktor.util.encodeBase64
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenResponse
import ru.somarov.berte.application.dto.auth.Token
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.infrastructure.uuid.createUniqueString
import ru.somarov.berte.ui.OAuthActivity

class GoogleProvider : OIDAndroidProvider<ActivityResult, Intent> {
    override fun formAuthProcess(store: TokenStore, settings: OAuthSettings, activity: OAuthActivity):
        OAuthProcess<ActivityResult, Intent> {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(settings.authorizationEndpoint),
            Uri.parse(settings.tokenEndpoint)
        )
        val authRequestBuilder = AuthorizationRequest.Builder(
            /* configuration = */ serviceConfig,
            /* clientId = */ settings.clientId,
            /* responseType = */ ResponseTypeValues.CODE,
            /* redirectUri = */ Uri.parse(settings.redirectUri)
        )
        val state = createUniqueString().encodeBase64()

        val authRequest = authRequestBuilder
            .also { builder ->
                settings.scope?.let {
                    builder.setScopes(it.split(" "))
                }
            }
            .setState(state)
            .build()

        val authService = AuthorizationService(activity)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)

        return OAuthProcess(
            { handleOAuthResult(it, activity, store) },
            ActivityResultContracts.StartActivityForResult(),
            authIntent
        )
    }

    override fun getProvider(): Token.TokenProvider {
        return Token.TokenProvider.GOOGLE
    }

    private suspend fun handleOAuthResult(
        result: ActivityResult,
        activity: OAuthActivity,
        store: TokenStore
    ) {
        val data = result.data
        val ex = AuthorizationException.fromIntent(data)

        if (data == null || ex != null) {
            store.set(null)
            return
        }

        val resp = AuthorizationResponse.fromIntent(data)
        val code = resp?.authorizationCode

        if (resp != null && code != null) {
            val authServiceTokenRequest = AuthorizationService(activity)
            authServiceTokenRequest.performTokenRequest(
                resp.createTokenExchangeRequest()
            ) { response, ex2 ->
                val token = getToken(response, ex2)
                activity.lifecycleScope.launch { store.set(token) }
            }
        } else {
            store.set(null)
        }
    }

    private fun getToken(response: TokenResponse?, ex2: AuthorizationException?): Token? {
        return if (ex2 != null) {
            null
        } else if (response != null) {
            val token = response.accessToken
            if (!token.isNullOrEmpty()) {
                Token(
                    token, null,
                    Token.TokenProvider.GOOGLE
                )
            } else {
                null
            }
        } else {
            null
        }
    }
}
