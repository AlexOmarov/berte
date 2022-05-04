package ru.somarov.berte_api.request

data class TokenRequest(val code: String, val clientId: String, val codeVerifier: String)
