package ru.somarov.auth.request

import ru.somarov.auth.dto.LoginType

data class LoginRequest(val login: String, val password: String, val codeChallenge: String, val type: LoginType)
