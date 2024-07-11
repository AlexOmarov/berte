package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import ru.somarov.berte.infrastructure.oauth.OAuthSettings

class LoginWithProvidersViewModel(val appViewModel: AppViewModel) : ViewModel() {

    /*    private val _result = MutableStateFlow<CommonResult<TokenStore>>(CommonResult.Empty())
        val result = _result.asStateFlow()*/

    fun loginWithGoogle(context: Any) {
        appViewModel.login(
            context,
            OAuthSettings(
                authorizationEndpoint = "https://accounts.google.com/o/oauth2/auth",
                tokenEndpoint = "https://oauth2.googleapis.com/token",
                clientId = "191033215032-36m9077g5pqd2l67i686qslb50mtveha.apps.googleusercontent.com",
                redirectUri = "ru.somarov.berte:/oauth2redirect",
                scope = "profile openid",
                tokenToService = null,
            )
        )
    }

    fun loginWithVk() {
        return
    }

    fun loginWithOk() {
        return
    }

    fun loginWithYandex(context: Any) {
        appViewModel.loginWithYandex(context)
    }

    fun loginWithYandexOauth(context: Any) {
        appViewModel.login(
            context,
            OAuthSettings(
                authorizationEndpoint = "https://oauth.yandex.ru/authorize",
                tokenEndpoint = "https://oauth.yandex.ru/token",
                clientId = "f083805e1e10440d800ab20001b4857c",
                redirectUri = "ru.somarov.berte:/oauth2redirect",
                scope = null,
                tokenToService = null,
            )
        )
    }

    fun loginWithTelegram() {
        return
    }

    fun loginWithApple() {
        return
    }
}
