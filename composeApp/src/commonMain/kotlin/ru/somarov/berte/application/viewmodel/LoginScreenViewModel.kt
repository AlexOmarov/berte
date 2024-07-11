package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.somarov.berte.infrastructure.network.CommonResult
import ru.somarov.berte.infrastructure.network.HttpClientProps
import ru.somarov.berte.infrastructure.network.asCommonResult
import ru.somarov.berte.infrastructure.network.getDefaultClient

class LoginScreenViewModel : ViewModel() {
    private val _accessToken: MutableStateFlow<CommonResult<String>> =
        MutableStateFlow(CommonResult.Empty())

    private val _tokenStorage = mutableListOf<BearerTokens>()
    private val _client = getDefaultClient(HttpClientProps(tokenStorage = _tokenStorage))

    private val _loginProgress: MutableStateFlow<CommonResult<String>> =
        MutableStateFlow(CommonResult.Empty())
    val loginProgress = _loginProgress.asStateFlow()

    /* suspend fun loginMock(username: String, password: String) {
         _loginProgress.emit(CommonResult.Loading())
         delay(LOGIN_MOCK_DELAY)
         _loginProgress.emit(
             CommonResult.Success("Иван Иванович")
         )
     }*/

    private suspend fun requestTokenByForm(username: String, password: String): String {
        _accessToken.emit(CommonResult.Loading())
        return _client
            .post(requestToken) {
                parameter("username", username)
                parameter("password", password)
            }
            .body<String>().also { _accessToken.emit(CommonResult.Success(it)) }
    }

    private suspend fun requestTokenByCookie(): String {
        _accessToken.emit(CommonResult.Loading())
        return _client
            .get(requestToken)
            .body<String>().also { _accessToken.emit(CommonResult.Success(it)) }
    }

    private suspend fun ping(token: String): String {
        return _client.get(ok).body<String>()
    }

    private suspend fun loginWith(
        username: String,
        password: String,
        request: suspend (String, String) -> String
    ) {
        _loginProgress.emit(CommonResult.Loading())
        _accessToken.emit(CommonResult.Loading())
        val result = runCatching { request(username, password) }.asCommonResult()
        _accessToken.emit(result)
        when (result) {
            is CommonResult.Success ->
                _loginProgress.emit(runCatching { ping(token = result.data) }.asCommonResult())

            else -> _loginProgress.emit(result)
        }
    }

    companion object {
        val requestToken = "https://api.twitter.com/oauth/request_token"
        val ok = "https://api.twitter.com/oauth/ok"
        const val LOGIN_MOCK_DELAY = 300L
    }
}
