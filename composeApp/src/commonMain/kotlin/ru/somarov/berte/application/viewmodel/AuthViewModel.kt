package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.somarov.berte.application.dto.auth.Provider
import ru.somarov.berte.application.dto.auth.Token
import ru.somarov.berte.application.dto.auth.User
import ru.somarov.berte.infrastructure.navigation.navigateTo
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.infrastructure.oauth.startOAuth
import ru.somarov.berte.infrastructure.uuid.createUniqueString
import ru.somarov.berte.ui.Route

class AuthViewModel(
    private val controller: NavHostController,
    private val client: HttpClient
) : ViewModel() {

    private val _authUser = MutableStateFlow<User?>(null)
    private val _state = TokenStore()
    val user = _authUser.asStateFlow()

    init {
        _state.tokenFlow
            .filterNotNull()
            .onEach {
                // TODO: try for user retrieval
                _authUser.emit(getUserFromToken(it))
                // TODO: not navigate, but issue a command to some sort of queue, which will be
                // executed after compose is loaded and drawn
                controller.navigateTo(Route.Home)
            }
            .launchIn(viewModelScope)
    }

    fun login(context: Any, provider: Provider) {
        viewModelScope.launch {
            val settings = getProviderSettings(provider)
            startOAuth(context, _state, settings)
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.set(
                Token(
                    value = "$username:$password",
                    expiresIn = null,
                    provider = Provider.PASSWORD
                )
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            _state.set(null)
            controller.navigateTo(Route.Login)
        }
    }

    private fun getUserFromToken(it: Token): User {
        // TODO: get user from token, throw exceptions if needed
        return User(
            id = createUniqueString(),
            username = "",
            email = emptyList(),
            token = it
        )
    }

    private fun getProviderSettings(provider: Provider): OAuthSettings? {
        return when (provider) {
            Provider.GOOGLE -> OAuthSettings(
                authorizationEndpoint = "https://accounts.google.com/o/oauth2/auth",
                tokenEndpoint = "https://oauth2.googleapis.com/token",
                clientId = "191033215032-36m9077g5pqd2l67i686qslb50mtveha.apps.googleusercontent.com",
                redirectUri = "ru.somarov.berte:/oauth2redirect",
                scope = "profile openid",
                tokenToService = null,
                provider = provider
            )

            Provider.YANDEX -> OAuthSettings(
                authorizationEndpoint = "",
                tokenEndpoint = "",
                clientId = "",
                redirectUri = "",
                scope = "",
                tokenToService = null,
                provider = provider
            )

            Provider.PASSWORD -> null
            else -> null
        }
    }
}
