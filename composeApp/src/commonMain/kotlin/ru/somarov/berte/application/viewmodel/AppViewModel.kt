package ru.somarov.berte.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.somarov.berte.UIScreen
import ru.somarov.berte.application.dto.AuthUser
import ru.somarov.berte.infrastructure.network.CommonResult
import ru.somarov.berte.infrastructure.network.YandexApi
import ru.somarov.berte.infrastructure.network.getOrNull
import ru.somarov.berte.infrastructure.network.toAuthUser
import ru.somarov.berte.infrastructure.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.oauth.OAuthState
import ru.somarov.berte.infrastructure.oauth.TokenSource
import ru.somarov.berte.infrastructure.oauth.TokenStore
import ru.somarov.berte.infrastructure.oauth.openAuthForResult
import ru.somarov.berte.infrastructure.oauth.openYandexAuthForResult
import ru.somarov.berte.infrastructure.util.createUUID

class AppViewModel(
    private val navController: NavHostController
) : ViewModel() {
    private val _authResult = MutableStateFlow<CommonResult<TokenStore>>(CommonResult.Empty())
    val authResult = _authResult.asStateFlow()
    private val _authUser = MutableStateFlow<AuthUser?>(null)
    val authUser = _authUser.asStateFlow()

    fun login(context: Any, settings: OAuthSettings) {
        viewModelScope.launch {
            val res = openAuthForResult(
                context,
                OAuthState(null, null),
                settings
            )
            _authResult.emit(res)
        }
    }

    fun loginWithYandex(context: Any) {
        viewModelScope.launch {
            val res = openYandexAuthForResult(
                context = context,
                state = OAuthState(null, null)
            )
            _authResult.emit(res)
        }
    }

    fun setAuthResult(result: CommonResult<TokenStore>) {
        viewModelScope.launch {
            _authResult.emit(result)
        }
    }

    init {
        authResult
            .onEach {
                it.getOrNull()?.let { tokenStore ->
                    when (tokenStore.source) {
                        TokenSource.GOOGLE -> {}
                        TokenSource.YANDEX ->
                            _authUser.emit(
                                YandexApi.getUserInfo(tokenStore).toAuthUser()
                            )

                        TokenSource.PASSWORD -> {}
                    }

                    navigateTo(UIScreen.Home)
                }
            }
            .launchIn(viewModelScope)
    }

    fun navigateTo(uiScreen: UIScreen) {
        viewModelScope.launch {
            if (uiScreen.screen.root) {
                navController.popBackStack()
            }
            navController.navigate(uiScreen.name)
        }
    }

    fun loginWithUserAndPassword(username: String, password: String) {
        viewModelScope.launch {
            _authResult.emit(
                CommonResult.Success(
                    TokenStore(
                        value = "",
                        expiresIn = null,
                        source = TokenSource.PASSWORD
                    )
                )
            )
            _authUser.emit(
                AuthUser(
                    id = createUUID(),
                    username = username,
                    email = emptyList()
                )
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            _authResult.emit(CommonResult.Empty())
            _authUser.emit(null)
            navigateTo(UIScreen.Login)
        }
    }
}
