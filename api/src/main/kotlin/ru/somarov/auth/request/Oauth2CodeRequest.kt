package ru.somarov.auth.request

import ru.somarov.auth.constant.Provider

data class Oauth2CodeRequest(
    val login: String,
    val secret: String,
    val codeChallenge: String,
    val clientId: String,
    val provider: Provider
)
