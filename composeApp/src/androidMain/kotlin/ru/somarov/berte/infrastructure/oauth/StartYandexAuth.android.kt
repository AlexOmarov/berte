package ru.somarov.berte.infrastructure.oauth

import android.content.Context
import ru.somarov.berte.OpenAuthActivity

actual fun startYandexAuth(
    context: Any,
    state: OAuthState
) {
    OpenAuthActivity.startYandex(context as Context, state)
}
