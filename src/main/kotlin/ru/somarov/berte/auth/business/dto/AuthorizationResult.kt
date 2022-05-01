package ru.somarov.berte.auth.business.dto

data class AuthorizationResult(val access: String, val refresh: String, val rememberMe: String?, val id: String?)
