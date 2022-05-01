package ru.somarov.auth.dto

import ru.somarov.auth.constant.BerteKeyType

data class BerteKey(val type: BerteKeyType, val kty: String, val use: String, val kid: String, val n: String)
