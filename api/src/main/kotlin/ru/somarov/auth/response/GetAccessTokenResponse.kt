package ru.somarov.auth.response

data class GetAccessTokenResponse(val access: String?, val refresh: String?, val remember: String?)
