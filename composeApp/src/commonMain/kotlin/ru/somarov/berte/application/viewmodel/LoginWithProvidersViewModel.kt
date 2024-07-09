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
//                {"installed":{"client_id":"191033215032-36m9077g5pqd2l67i686qslb50mtveha.apps.googleusercontent.com","project_id":"berte-428917","auth_uri":"https://accounts.google.com/o/oauth2/auth","token_uri":"https://oauth2.googleapis.com/token","auth_provider_x509_cert_url":"https://www.googleapis.com/oauth2/v1/certs"}}
                OAuthSettings(
                    authorizationEndpoint = "https://accounts.google.com/o/oauth2/auth",
                    tokenEndpoint = "https://oauth2.googleapis.com/token",
                    clientId = "191033215032-36m9077g5pqd2l67i686qslb50mtveha.apps.googleusercontent.com",
                    redirectUri = "",
                    scope = "userinfo profile",
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

    fun loginWithYandex() {
        return Unit
    }

    fun loginWithTelegram() {
        return Unit
    }

    fun loginWithApple() {
        return Unit
    }
}
