package ru.somarov.berte.infrastructure.oauth

expect fun startOAuth(context: Any, state: TokenStore, provider: OIDProvider)
