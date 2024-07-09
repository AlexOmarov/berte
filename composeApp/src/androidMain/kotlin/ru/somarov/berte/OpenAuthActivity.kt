package ru.somarov.berte

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import io.ktor.util.encodeBase64
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import ru.somarov.berte.infrastructure.createUUID
import ru.somarov.berte.infrastructure.network.CommonResult
import ru.somarov.berte.infrastructure.network.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.network.oauth.OAuthState
import ru.somarov.berte.ui.WaitBox

class OpenAuthActivity : AppCompatActivity() {
    private fun setStateAndExit(result: CommonResult<String>) {
        val currentState = state
        if (currentState == null) {
            finish()
            return
        }
        currentState.setTokenWithResult(result) {
            finish()
        }
    }

    private val resultOauth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val data: Intent = result.data ?: run {
                setStateAndExit(CommonResult.Error(NotFoundException()))
                return@registerForActivityResult
            }
            val resp = AuthorizationResponse.fromIntent(data)
            val ex = AuthorizationException.fromIntent(data)
            val code = resp?.authorizationCode
            if (ex != null) {
                setStateAndExit(CommonResult.Error(ex))
            }
            if (resp != null && code != null) {
                val authService = AuthorizationService(this)
                authService.performTokenRequest(
                    resp.createTokenExchangeRequest()
                ) { response, ex2 ->
                    if (response != null) {
                        val token = response.accessToken
                        if (!token.isNullOrEmpty()) {
                            setStateAndExit(CommonResult.Success(token))
                        } else {
                            setStateAndExit(CommonResult.Error(NotFoundException()))
                        }
                    }
                    if (ex2 != null) {
                        setStateAndExit(CommonResult.Error(ex2))
                    }
                }
            } else {
                setStateAndExit(
                    CommonResult.Error(CanceledException())
                )
            }
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
        val state = createUUID().encodeBase64()
        val timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            .toString()

        val string =
            "${openAuthConfig.clientId}${openAuthConfig.scope}${timestamp}${state}${openAuthConfig.redirectUri}"

        val authRequest = authRequestBuilder
            .setScopes(openAuthConfig.scope.split(" "))
            .setAdditionalParameters(
                mapOf("client_secret" to string.encodeBase64())
            )
            .setState(state)
            .build()

        val authService = AuthorizationService(this)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
        resultOauth.launch(authIntent)
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//
//        }.launch(authIntent)
    }

    private var state: OAuthState? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        state = staticState
        staticState = null
        val json = intent.getStringExtra("SETTINGS")
        if (json != null) {
            val settings = Json.decodeFromString<OAuthSettings>(json)
            start(settings)
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
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
        fun start(context: Context, state: OAuthState, settings: OAuthSettings) {
            staticState = state
            val json = Json.encodeToString(settings)
            context.startActivity(
                Intent(context, OpenAuthActivity::class.java).also {
                    it.putExtra("SETTINGS", json)
                }
            )
        }

        private var staticState: OAuthState? = null
    }
}

typealias CanceledException = kotlinx.coroutines.CancellationException

class NotFoundException : Exception()
