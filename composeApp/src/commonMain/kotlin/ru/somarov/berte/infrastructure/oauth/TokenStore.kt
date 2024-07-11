package ru.somarov.berte.infrastructure.oauth

class TokenStore(
    val value: String,
    val expiresIn: Long?,
    val source: TokenSource
)
