package ru.somarov.berte_api.request

import java.io.Serializable

data class LogoutRequest(val access: String, val refresh: String, val rememberMe: String?, val id: String?): Serializable
