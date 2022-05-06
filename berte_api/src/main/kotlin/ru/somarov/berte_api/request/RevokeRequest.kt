package ru.somarov.berte_api.request

import ru.somarov.berte_api.standard.BerteRequest
import java.io.Serializable

data class RevokeRequest(val access: String?, val refresh: String?, val rememberMe: String?, val id: String?): BerteRequest(),
    Serializable
