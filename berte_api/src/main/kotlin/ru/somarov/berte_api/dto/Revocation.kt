package ru.somarov.berte_api.dto

import ru.somarov.berte_api.constant.TokenType

data class Revocation(val token: String, val type: TokenType)