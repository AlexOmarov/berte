package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.somarov.berte.application.dto.auth.AuthUser
import ru.somarov.berte.application.dto.auth.TokenInfo
import ru.somarov.berte.application.dto.auth.TokenProvider
import ru.somarov.berte.infrastructure.navigation.Navigation
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.infrastructure.oauth.startOAuth
import ru.somarov.berte.ui.Screen

class AuthViewModel(private val controller: NavHostController) : ViewModel() {

    private val _authUser = MutableStateFlow<AuthUser?>(null)
    val authUser = _authUser.asStateFlow()

    init {
        authUser
            .filterNotNull()
            .onEach { Navigation.to(Screen.Home, controller) }
            .launchIn(viewModelScope)
    }

    fun login(context: Any, provider: TokenProvider) {
        viewModelScope.launch {
            val settings = getProviderSettings(provider)
            val state = TokenStore()
            startOAuth(context, state, settings)
            val result = state.tokenFlow.firstOrNull()
            val res = result?.let {
                AuthUser(
                    id = uuid4().toString(),
                    username = "",
                    email = emptyList(),
                    result
                )
            }
            res?.let { _authUser.emit(res) }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val res = AuthUser(
                id = uuid4().toString(),
                username = "username",
                email = emptyList(),
                TokenInfo(
                    value = "$username:$password",
                    expiresIn = null,
                    provider = TokenProvider.PASSWORD
                )
            )
            _authUser.emit(res)
        }
    }

    fun logout() {
        viewModelScope.launch {
            _authUser.emit(null)
            Navigation.to(Screen.Login, controller)
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

            TokenProvider.YANDEX -> null
            TokenProvider.PASSWORD -> null
            else -> null
        }
    }
}
