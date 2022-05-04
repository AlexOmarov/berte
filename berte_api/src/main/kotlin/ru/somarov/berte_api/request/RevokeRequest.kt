package ru.somarov.berte_api.request

data class RevokeRequest(val access: String?, val refresh: String?, val rememberMe: String?, val id: String?)
