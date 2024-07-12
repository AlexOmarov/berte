package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.somarov.berte.application.dto.auth.AuthUser
import ru.somarov.berte.application.dto.auth.TokenInfo
import ru.somarov.berte.application.dto.auth.TokenProvider
import ru.somarov.berte.infrastructure.navigation.navigateTo
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.infrastructure.oauth.startOAuth
import ru.somarov.berte.infrastructure.uuid.UUID
import ru.somarov.berte.ui.Screen

class AuthViewModel(private val controller: NavHostController) : ViewModel() {

    private val _authUser = MutableStateFlow<AuthUser?>(null)
    private val _state = TokenStore()
    val authUser = _authUser.asStateFlow()

    init {
        _state.tokenFlow
            .filterNotNull()
            .onEach {
                _authUser.emit(
                    AuthUser(
                        id = UUID.generate().toString(),
                        username = "",
                        email = emptyList(),
                        it
                    )
                )
            }
            .launchIn(viewModelScope)

        authUser
            .filterNotNull()
            .onEach { controller.navigateTo(Screen.Home) }
            .launchIn(viewModelScope)
    }

    fun login(context: Any, provider: TokenProvider) {
        viewModelScope.launch {
            val settings = getProviderSettings(provider)
            startOAuth(context, _state, settings)
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.setToken(
                TokenInfo(
                    value = "$username:$password",
                    expiresIn = null,
                    provider = TokenProvider.PASSWORD
                )
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            _authUser.emit(null)
            controller.navigateTo(Screen.Login)
        }
    }

    private fun getProviderSettings(provider: TokenProvider): OAuthSettings? {
        return when (provider) {
            TokenProvider.GOOGLE -> OAuthSettings(
                authorizationEndpoint = "https://accounts.google.com/o/oauth2/auth",
                tokenEndpoint = "https://oauth2.googleapis.com/token",
                clientId = "191033215032-36m9077g5pqd2l67i686qslb50mtveha.apps.googleusercontent.com",
                redirectUri = "ru.somarov.berte:/oauth2redirect",
                scope = "profile openid",
                tokenToService = null,
                provider = provider
            )

            TokenProvider.YANDEX -> OAuthSettings(
                authorizationEndpoint = "",
                tokenEndpoint = "",
                clientId = "",
                redirectUri = "",
                scope = "",
                tokenToService = null,
                provider = provider
            )

            TokenProvider.PASSWORD -> null
            else -> null
        }
    }
}
