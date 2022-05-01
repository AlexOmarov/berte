package ru.somarov.auth.dto

import ru.somarov.auth.constant.TokenType

data class Revocation(val token: String, val type: TokenType)