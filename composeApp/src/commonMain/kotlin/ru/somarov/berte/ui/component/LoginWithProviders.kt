package ru.somarov.berte.ui.component

import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource
import ru.somarov.berte.application.viewmodel.AppViewModel
import ru.somarov.berte.application.viewmodel.LoginWithProvidersViewModel
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
