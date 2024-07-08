package ru.somarov.berte.application.compose.screen

import androidx.lifecycle.ViewModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.somarov.berte.application.compose.Endpoint
import ru.somarov.berte.infrastructure.BResult
import ru.somarov.berte.infrastructure.asBResult
import ru.somarov.berte.infrastructure.getOrNull
import ru.somarov.berte.infrastructure.network.Engine
import ru.somarov.berte.infrastructure.network.Engine.installBasic
import ru.somarov.berte.infrastructure.network.Engine.installBearer

class LoginScreenViewModel : ViewModel() {
    private val _accessToken: MutableStateFlow<BResult<String>> = MutableStateFlow(BResult.Empty())
    private val _loginProgress: MutableStateFlow<BResult<String>> =
        MutableStateFlow(BResult.Empty())

    val loginProgress = _loginProgress.asStateFlow()

    private suspend fun loginWith(username: String, password: String, request: suspend (String, String) -> String) {
        _loginProgress.emit(BResult.Loading())
        _accessToken.emit(BResult.Loading())
        val token = kotlin.runCatching {
            request(username, password)
//            requestTokenByBasic(username, password)
        }.asBResult()
        _accessToken.emit(token)
        when (token) {
            is BResult.Success -> {
                _loginProgress.emit(
                    kotlin.runCatching { ping(accessToken = token.data) }.asBResult()
                )
            }
            else -> {
                _loginProgress.emit(token)
            }
        }
    }

    private suspend fun requestTokenByBasic(username: String, password: String): String {
        return Engine.clientCommon {
            installBasic(username, password)
        }
            .get(Endpoint.requestToken)
            .body<String>()
    }

    private suspend fun requestTokenByForm(username: String, password: String): String {
        return Engine.clientCommon()
            .post(Endpoint.requestToken) {
                parameter("username", username)
                parameter("password", password)
            }
            .body<String>()
    }

    private suspend fun requestTokenByCookie(): String {
        _accessToken.emit(BResult.Loading())
        return Engine.clientCommon()
            .get(Endpoint.requestToken)
            .body<String>().also {
                _accessToken.emit(BResult.Success(it))
            }

    }

    private suspend fun ping(accessToken: String): String {
        return Engine.clientCommon() {
            installBearer(accessToken = accessToken)
        }.get(Endpoint.ok)
            .body<String>()
    }

    suspend fun loginWithForm(username: String, password: String) {
        loginWith(username, password, ::requestTokenByForm)
    }
    suspend fun loginWithBasic(username: String, password: String) {
        loginWith(username, password, ::requestTokenByBasic)
    }

    suspend fun loginWithSawedToken() {
        _loginProgress.emit(BResult.Loading())
        _loginProgress.emit(
            runCatching {
                ping(accessToken = _accessToken.value.getOrNull() ?: requestTokenByCookie())
            }.asBResult()
        )
    }

    suspend fun logout() {
        _accessToken.emit(BResult.Empty())
        _loginProgress.emit(BResult.Empty())
    }

    suspend fun loginMock(username: String, password: String)  {
        _loginProgress.emit(BResult.Loading())
        delay(300)
        _loginProgress.emit(
            BResult.Success("Иван Иванович")
        )
    }
}





