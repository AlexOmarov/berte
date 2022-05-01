package ru.somarov.auth.response

data class TokenResponse(private val access: String, private val refresh: String, private val rememberMe: String?, private val id: String?)