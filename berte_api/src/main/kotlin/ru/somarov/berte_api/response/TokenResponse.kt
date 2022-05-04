package ru.somarov.berte_api.response

data class TokenResponse(private val access: String, private val refresh: String, private val rememberMe: String?, private val id: String?)