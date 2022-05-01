package ru.somarov.auth.request

data class TokenRequest(val code: String, val clientId: String, val codeVerifier: String)
