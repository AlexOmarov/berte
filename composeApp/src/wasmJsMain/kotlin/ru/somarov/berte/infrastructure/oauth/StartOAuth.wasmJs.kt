package ru.somarov.berte.infrastructure.oauth

actual fun startOAuth(
    context: Any,
    state: TokenStore,
    provider: OIDProvider
) {
    println("$state $context $provider")
}
