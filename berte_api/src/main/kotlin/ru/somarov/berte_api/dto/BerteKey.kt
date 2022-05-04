package ru.somarov.berte_api.dto

import ru.somarov.berte_api.constant.BerteKeyType

data class BerteKey(val type: BerteKeyType, val kty: String, val use: String, val kid: String, val n: String)
