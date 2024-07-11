package ru.somarov.berte.infrastructure.oauth

actual fun startOAuth(
    context: Any,
    state: TokenStore,
    settings: OAuthSettings?
) {
    return Unit
}
