package ru.somarov.berte_api.request

import ru.somarov.berte_api.constant.Provider

data class Oauth2CodeRequest(
    val login: String,
    val secret: String,
    val codeChallenge: String,
    val clientId: String,
    val provider: Provider
)
