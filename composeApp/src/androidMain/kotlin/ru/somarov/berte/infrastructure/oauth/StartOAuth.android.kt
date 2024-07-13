package ru.somarov.berte.infrastructure.oauth

import android.content.Context
import ru.somarov.berte.ui.OAuthStarterActivity

actual fun startOAuth(
    context: Any,
    state: TokenStore,
    settings: OAuthSettings?
) {
    OAuthStarterActivity.start(context as Context, state, settings)
}
