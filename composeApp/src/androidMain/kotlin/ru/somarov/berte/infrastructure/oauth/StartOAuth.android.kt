package ru.somarov.berte.infrastructure.oauth

import android.content.Context
import android.content.Intent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.somarov.berte.ui.OAuthActivity

actual fun startOAuth(
    context: Any,
    state: TokenStore,
    provider: OIDProvider
) {
    staticState = state
    (context as Context).startActivity(
        Intent(context, OAuthActivity::class.java).also {
            it.putExtra("PROVIDER", Json.encodeToString(provider))
        }
    )
}

var staticState: TokenStore? = null
