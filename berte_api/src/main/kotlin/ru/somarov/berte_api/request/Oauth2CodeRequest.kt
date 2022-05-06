package ru.somarov.berte_api.request

import ru.somarov.berte_api.constant.Provider
import ru.somarov.berte_api.standard.BerteRequest
import java.io.Serializable

data class Oauth2CodeRequest(
    val login: String,
    val secret: String,
    val codeChallenge: String,
    val clientId: String,
    val provider: Provider
): BerteRequest(), Serializable
