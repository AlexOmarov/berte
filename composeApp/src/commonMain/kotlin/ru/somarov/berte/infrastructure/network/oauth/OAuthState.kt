package ru.somarov.berte.infrastructure.network.oauth

import io.ktor.client.plugins.cookies.CookiesStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.somarov.berte.infrastructure.network.CommonResult

class OAuthState(
    val cookiesStorage: CookiesStorage?,
    val tokenToServiceUrl: String?
) {
    private val _tokenFlow = MutableStateFlow<CommonResult<String>>(CommonResult.Empty())
    val tokenFlow = _tokenFlow.asStateFlow()
    fun setTokenWithResult(token: CommonResult<String>, done: () -> Unit) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            _tokenFlow.emit(token)
            done()
        }
    }
}