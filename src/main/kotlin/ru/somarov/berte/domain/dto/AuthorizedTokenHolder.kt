package ru.somarov.berte.domain.dto

data class AuthorizedTokenHolder(var access: String?, var refresh: String?, var rememberMe: String?, var id: String?)