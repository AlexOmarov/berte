package ru.somarov.berte.domain.dto

import java.util.*

data class AuthorizationCodeInfo(val username: String, val code: String, val clientId: String, val codeChallenge: String)