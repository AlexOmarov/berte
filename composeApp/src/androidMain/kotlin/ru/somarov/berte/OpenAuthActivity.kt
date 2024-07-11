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
import com.benasher44.uuid.uuid4
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
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
import ru.somarov.berte.application.dto.auth.TokenProvider
import ru.somarov.berte.infrastructure.network.CommonResult
import ru.somarov.berte.infrastructure.network.getOrNull
import ru.somarov.berte.infrastructure.oauth.CanceledRequestException
import ru.somarov.berte.infrastructure.oauth.NotFoundException
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.infrastructure.oauth.TokenStore.*
import ru.somarov.berte.ui.element.WaitBox

class OpenAuthActivity : AppCompatActivity() {
    private fun setStateAndExit(currentState: TokenStore, result: CommonResult<TokenInfo>) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            currentState.setToken(result.getOrNull())
        }
    }

    private fun handleYandexResult(result: YandexAuthResult) {
        val state = staticState ?: return
        staticState = null
        when (result) {
            is YandexAuthResult.Success -> {
                val info = TokenInfo(result.token.value, result.token.expiresIn, TokenProvider.YANDEX)
                lifecycleScope.launch {
                    setStateAndExit(state, CommonResult.Success(info))
                }
            }

            is YandexAuthResult.Failure ->
                setStateAndExit(state, CommonResult.Error(result.exception))

            YandexAuthResult.Cancelled ->
                setStateAndExit(state, CommonResult.Error(CanceledRequestException()))
        }
    }

    private fun startYandex() {
        val loginOptions = YandexAuthLoginOptions(
            loginType = LoginType.NATIVE
        )
        val sdk: YandexAuthSdk =
            YandexAuthSdk.create(YandexAuthOptions(context = this.applicationContext, false))
        val yandexLauncher =
            registerForActivityResult(sdk.contract) { result -> handleYandexResult(result) }
        yandexLauncher.launch(loginOptions)
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
                        setStateAndExit(state, CommonResult.Success(TokenInfo(token, null, TokenProvider.GOOGLE)))
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

    private val resultOauth =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            handleOAuthResult(result)
        }

    private fun start(openAuthConfig: OAuthSettings) {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(openAuthConfig.authorizationEndpoint),
            Uri.parse(openAuthConfig.tokenEndpoint)
        )
        val authRequestBuilder = AuthorizationRequest.Builder(
            /* configuration = */ serviceConfig,
            /* clientId = */ openAuthConfig.clientId,
            /* responseType = */ ResponseTypeValues.CODE,
            /* redirectUri = */ Uri.parse(openAuthConfig.redirectUri)
        )
        val state = uuid4().toString().encodeBase64()

        val authRequest = authRequestBuilder
            .also { builder ->
                openAuthConfig.scope?.let {
                    builder.setScopes(it.split(" "))
                }
            }
            .setState(state)
            .build()

        val authService = AuthorizationService(this)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
        resultOauth.launch(authIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val json = intent.getStringExtra("SETTINGS")
        val yandex = intent.getBooleanExtra("YANDEX", false)

        when {
            json != null -> {
                start(Json.decodeFromString<OAuthSettings>(json))
            }

            yandex -> {
                startYandex()
            }
        }
        val view = ComposeView(this).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy
                    .DisposeOnViewTreeLifecycleDestroyed
            )

            setContent {
                MaterialTheme {
                    WaitBox()
                }
            }
        }
        setContentView(view)
    }

    companion object {
        fun start(context: Context, state: TokenStore, settings: OAuthSettings?) {
            staticState = state
            val json = Json.encodeToString(settings)
            context.startActivity(
                Intent(context, OpenAuthActivity::class.java).also {
                    it.putExtra("SETTINGS", json)
                }
            )
        }

        fun startYandex(context: Context, state: TokenStore) {
            staticState = state
            context.startActivity(
                Intent(context, OpenAuthActivity::class.java).also {
                    it.putExtra("YANDEX", true)
                }
            )
        }

        private var staticState: TokenStore? = null
    }
}
