package ru.somarov.berte_api.request

import ru.somarov.berte_api.constant.GrantType
import ru.somarov.berte_api.constant.Provider

data class Oauth2CodeRequest(
    val login: String?,
    val secret: String?,
    val codeChallenge: String,
    val grantType: GrantType,
    val clientId: String,
    val provider: Provider
)
