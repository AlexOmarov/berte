package ru.somarov.berte.ui.component

import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import ru.somarov.berte.UIScreen
import ru.somarov.berte.application.viewmodel.AppViewModel
import ru.somarov.berte.infrastructure.network.CommonResult
import ru.somarov.berte.infrastructure.network.oauth.OAuthSettings
import ru.somarov.berte.infrastructure.network.oauth.OAuthState
import ru.somarov.berte.infrastructure.network.oauth.openAuthForResult
import ru.somarov.berte.ui.rememberContext
import ru.somarov.library.resources.Res
import ru.somarov.library.resources.apple
import ru.somarov.library.resources.google
import ru.somarov.library.resources.ok
import ru.somarov.library.resources.telegram
import ru.somarov.library.resources.vk
import ru.somarov.library.resources.yandex

@Composable
internal fun LoginWithProviders(
    viewModel: AppViewModel,
    localViewModel: LoginWithProvidersViewModel = viewModel {
        LoginWithProvidersViewModel(viewModel)
    }
) {
    val context = rememberContext()
    FilledTonalIconButton(onClick = {
        localViewModel.loginWithGoogle(context)
    }) {
        Icon(painter = painterResource(Res.drawable.google), contentDescription = "Google")
    }
    FilledTonalIconButton(onClick = {
        localViewModel.loginWithYandex()
    }) {
        Icon(painter = painterResource(Res.drawable.yandex), contentDescription = "Yandex")
    }
    FilledTonalIconButton(onClick = {
        localViewModel.loginWithApple()
    }) {
        Icon(painter = painterResource(Res.drawable.apple), contentDescription = "Apple")
    }
    FilledTonalIconButton(onClick = {
        localViewModel.loginWithOk()
    }) {
        Icon(painter = painterResource(Res.drawable.ok), contentDescription = "OK")
    }
    FilledTonalIconButton(onClick = {
        localViewModel.loginWithTelegram()
    }) {
        Icon(painter = painterResource(Res.drawable.telegram), contentDescription = "Telegram")
    }
    FilledTonalIconButton(onClick = {
        localViewModel.loginWithVk()
    }) {
        Icon(painter = painterResource(Res.drawable.vk), contentDescription = "VK")
    }
}

class LoginWithProvidersViewModel(val viewModel: AppViewModel) : ViewModel() {
    private val _result = MutableStateFlow<CommonResult<String>>(CommonResult.Empty())
    val result = _result.asStateFlow()

    fun loginWithGoogle(context: Any) {
        viewModelScope.launch {
            val res: CommonResult<String> = openAuthForResult(
                context,
                OAuthState(null, null),
                OAuthSettings(
                    authorizationEndpoint = "https://accounts.google.com/o/oauth2/v2/auth",
                    tokenEndpoint = "https://oauth2.googleapis.com/token",
                    clientId = "89fpkm9v6ndc6z1usqzu6p6my43p774q",
                    redirectUri = "http://localhost:8080",
                    scope = "userinfo.profile",
                    tokenToService = "https://www.googleapis.com/oauth2/v4/token",
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
