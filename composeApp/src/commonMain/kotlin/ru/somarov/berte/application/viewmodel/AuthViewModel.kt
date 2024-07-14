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
import ru.somarov.berte.application.dto.auth.Token
import ru.somarov.berte.application.dto.auth.User
import ru.somarov.berte.infrastructure.navigation.navigateTo
import ru.somarov.berte.infrastructure.oauth.OIDProvider
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.infrastructure.oauth.startOAuth
import ru.somarov.berte.infrastructure.uuid.createUniqueString
import ru.somarov.berte.ui.Route

class AuthViewModel(
    private val controller: NavHostController,
    private val client: HttpClient,
    private val tokenStore: TokenStore
) : ViewModel() {

    private val _authUser = MutableStateFlow<User?>(null)
    val user = _authUser.asStateFlow()

    init {
        tokenStore.tokenFlow
            .filterNotNull()
            .onEach {
                _authUser.emit(getUserFromToken(it))
                // TODO: not navigate, but issue a command to some sort of queue, which will be
                // executed after compose is loaded and drawn
                controller.navigateTo(Route.Home)
            }
            .launchIn(viewModelScope)
    }

    fun login(context: Any, provider: OIDProvider) {
        viewModelScope.launch {
            startOAuth(context, tokenStore, provider)
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            tokenStore.set(
                Token(
                    value = "$username:$password",
                    expiresIn = null,
                    tokenProvider = Token.TokenProvider.PASSWORD
                )
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenStore.set(null)
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
}
