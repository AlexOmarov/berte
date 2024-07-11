package ru.somarov.berte.ui.element

import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import ru.somarov.berte.application.dto.auth.TokenProvider
import ru.somarov.berte.application.viewmodel.AuthViewModel
import ru.somarov.berte.infrastructure.ui.rememberContext
import ru.somarov.library.resources.Res
import ru.somarov.library.resources.apple
import ru.somarov.library.resources.google
import ru.somarov.library.resources.ok
import ru.somarov.library.resources.telegram
import ru.somarov.library.resources.vk
import ru.somarov.library.resources.yandex

@Composable
internal fun LoginProviders(viewModel: AuthViewModel) {
    val context = rememberContext()

    FilledTonalIconButton(onClick = { viewModel.login(context, TokenProvider.GOOGLE) }) {
        Icon(painter = painterResource(Res.drawable.google), contentDescription = "Google")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, TokenProvider.YANDEX) }) {
        Icon(painter = painterResource(Res.drawable.yandex), contentDescription = "Yandex")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, TokenProvider.APPLE) }) {
        Icon(painter = painterResource(Res.drawable.apple), contentDescription = "Apple")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, TokenProvider.OK) }) {
        Icon(painter = painterResource(Res.drawable.ok), contentDescription = "OK")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, TokenProvider.TELEGRAM) }) {
        Icon(painter = painterResource(Res.drawable.telegram), contentDescription = "Telegram")
    }
    FilledTonalIconButton(onClick = { viewModel.login(context, TokenProvider.VK) }) {
        Icon(painter = painterResource(Res.drawable.vk), contentDescription = "VK")
    }
}
