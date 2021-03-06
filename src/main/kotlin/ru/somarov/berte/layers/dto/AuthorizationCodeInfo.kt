package ru.somarov.berte.layers.dto

import java.util.*

data class AuthorizationCodeInfo(val userId: UUID, val username: String, val code: String, val clientId: String, val codeChallenge: String, val oidc: Boolean)