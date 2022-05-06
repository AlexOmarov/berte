package ru.somarov.berte_api.request

import ru.somarov.berte_api.constant.GrantType
import ru.somarov.berte_api.standard.BerteRequest
import java.io.Serializable

data class TokenRequest(val code: String, val clientId: String, val codeVerifier: String, val grantType: GrantType):
    BerteRequest(), Serializable
