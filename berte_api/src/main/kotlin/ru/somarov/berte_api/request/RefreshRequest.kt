package ru.somarov.berte_api.request

import ru.somarov.berte_api.standard.BerteRequest
import java.io.Serializable

data class RefreshRequest(val token: String): BerteRequest(), Serializable
