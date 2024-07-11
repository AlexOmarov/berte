package ru.somarov.berte.infrastructure.oauth

import android.content.Context
import ru.somarov.berte.OpenAuthActivity

actual fun startOAuth(
    context: Any,
    state: OAuthState,
    settings: OAuthSettings
) {
    OpenAuthActivity.start(context as Context, state, settings)
}