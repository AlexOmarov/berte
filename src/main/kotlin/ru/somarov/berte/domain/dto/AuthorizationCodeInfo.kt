package ru.somarov.berte.domain.dto

import java.util.*

data class AuthorizationCodeInfo(val id: UUID, val code: String, val clientId: String, val codeChallenge: String)