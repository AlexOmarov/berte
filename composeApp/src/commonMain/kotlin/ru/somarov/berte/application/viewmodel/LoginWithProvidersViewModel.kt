package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.somarov.berte.UIScreen
import ru.somarov.berte.infrastructure.network.CommonResult
import ru.somarov.berte.infrastructure.network.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.network.oauth.OAuthState
import ru.somarov.berte.infrastructure.network.oauth.openAuthForResult

class LoginWithProvidersViewModel(private val viewModel: AppViewModel) : ViewModel() {
    private val _result = MutableStateFlow<CommonResult<String>>(CommonResult.Empty())
    val result = _result.asStateFlow()

    fun loginWithGoogle(context: Any) {
        viewModelScope.launch {
            val res: CommonResult<String> = openAuthForResult(
                context,
                OAuthState(null, null),
                OAuthSettings(
                    authorizationEndpoint = "https://accounts.google.com/o/oauth2/auth",
                    tokenEndpoint = "https://oauth2.googleapis.com/token",
                    clientId = "191033215032-36m9077g5pqd2l67i686qslb50mtveha.apps.googleusercontent.com",
                    redirectUri = "ru.somarov.berte:/oauth2redirect",
                    scope = "profile openid",
                    tokenToService = null,
                )
            )
            if (res is CommonResult.Success) {
                viewModel.navigateTo(UIScreen.Home)
            }
            _result.emit(res)
        }
    }

    fun loginWithVk() {
        return Unit
    }

    fun loginWithOk() {
        return Unit
    }

    fun loginWithYandex(context: Any) {
        viewModelScope.launch {
            val res: CommonResult<String> = openAuthForResult(
                context,
                OAuthState(null, null),
                OAuthSettings(
                    authorizationEndpoint = "https://oauth.yandex.ru/authorize",
                    tokenEndpoint = "https://oauth.yandex.ru/token",
                    clientId = "f083805e1e10440d800ab20001b4857c",
                    redirectUri = "ru.somarov.berte:/oauth2redirect",
                    scope = null,
                    tokenToService = null,
                )
            )
            if (res is CommonResult.Success) {
                viewModel.navigateTo(UIScreen.Home)
            }
            _result.emit(res)
        }
    }

    fun loginWithTelegram() {
        return Unit
    }

    fun loginWithApple() {
        return Unit
    }
}
