package ru.somarov.berte

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.lifecycleScope
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthResult.Cancelled
import com.yandex.authsdk.YandexAuthResult.Failure
import com.yandex.authsdk.YandexAuthResult.Success
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.internal.strategy.LoginType
import io.ktor.util.encodeBase64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import ru.somarov.berte.application.dto.auth.TokenInfo
import ru.somarov.berte.application.dto.auth.TokenProvider.GOOGLE
import ru.somarov.berte.application.dto.auth.TokenProvider.YANDEX
import ru.somarov.berte.infrastructure.network.CommonResult
import ru.somarov.berte.infrastructure.network.getOrNull
import ru.somarov.berte.infrastructure.oauth.CanceledRequestException
import ru.somarov.berte.infrastructure.oauth.NotFoundException
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.infrastructure.uuid.UUID
import ru.somarov.berte.ui.element.WaitBox

class OpenAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val json = intent.getStringExtra("SETTINGS")!!

        start(Json.decodeFromString<OAuthSettings>(json))

        val view = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent { MaterialTheme { WaitBox() } }
        }

        setContentView(view)
    }

    private fun start(config: OAuthSettings) {
        when (config.provider) {
            YANDEX -> startYandex()
            else -> startGoogle(config)
        }
    }

    private fun startGoogle(config: OAuthSettings) {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(config.authorizationEndpoint),
            Uri.parse(config.tokenEndpoint)
        )
        val authRequestBuilder = AuthorizationRequest.Builder(
            /* configuration = */ serviceConfig,
            /* clientId = */ config.clientId,
            /* responseType = */ ResponseTypeValues.CODE,
            /* redirectUri = */ Uri.parse(config.redirectUri)
        )
        val state = UUID.generate().toString().encodeBase64()

        val authRequest = authRequestBuilder
            .also { builder ->
                config.scope?.let {
                    builder.setScopes(it.split(" "))
                }
            }
            .setState(state)
            .build()

        val authService = AuthorizationService(this)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            handleOAuthResult(it)
        }.launch(authIntent)
    }

    private fun startYandex() {
        val loginOptions = YandexAuthLoginOptions(loginType = LoginType.NATIVE)
        val sdk = YandexAuthSdk.create(YandexAuthOptions(this.applicationContext, false))
        val yandexLauncher = registerForActivityResult(sdk.contract) { handleYandexResult(it) }
        yandexLauncher.launch(loginOptions)
    }

    private fun handleYandexResult(result: YandexAuthResult) {
        val state = staticState ?: return
        staticState = null
        when (result) {
            is Success -> {
                val info = TokenInfo(result.token.value, result.token.expiresIn, YANDEX)
                lifecycleScope.launch { setStateAndExit(state, CommonResult.Success(info)) }
            }

            is Failure -> setStateAndExit(state, CommonResult.Error(result.exception))

            Cancelled -> setStateAndExit(state, CommonResult.Error(CanceledRequestException()))
        }
    }

    private fun setStateAndExit(currentState: TokenStore, result: CommonResult<TokenInfo>) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            currentState.setToken(result.getOrNull())
            finish()
        }
    }

    @Suppress("CyclomaticComplexMethod")
    private fun handleOAuthResult(result: ActivityResult) {
        val state = staticState ?: return
        staticState = null
        val data: Intent = result.data ?: run {
            setStateAndExit(state, CommonResult.Error(NotFoundException()))
            return
        }
        val resp = AuthorizationResponse.fromIntent(data)
        val ex = AuthorizationException.fromIntent(data)
        val code = resp?.authorizationCode
        if (ex != null) {
            setStateAndExit(state, CommonResult.Error(ex))
        }
        if (resp != null && code != null) {
            val authServiceTokenRequest = AuthorizationService(this)
            authServiceTokenRequest.performTokenRequest(
                resp.createTokenExchangeRequest()
            ) { response, ex2 ->
                if (response != null) {
                    val token = response.accessToken
                    if (!token.isNullOrEmpty()) {
                        setStateAndExit(state, CommonResult.Success(TokenInfo(token, null, GOOGLE)))
                    } else {
                        setStateAndExit(state, CommonResult.Error(NotFoundException()))
                    }
                }
                if (ex2 != null) {
                    setStateAndExit(state, CommonResult.Error(ex2))
                }
            }
        } else {
            setStateAndExit(state, CommonResult.Error(CanceledRequestException()))
        }
    }

    companion object {
        fun start(context: Context, state: TokenStore, settings: OAuthSettings?) {
            staticState = state
            context.startActivity(
                Intent(context, OpenAuthActivity::class.java).also {
                    it.putExtra("SETTINGS", Json.encodeToString(settings))
                }
            )
        }

        private var staticState: TokenStore? = null
    }
}
