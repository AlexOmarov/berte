package ru.somarov.auth.request

data class GetAccessTokenRequest(val code: String, val clientId: String, val clientSecret: String)
