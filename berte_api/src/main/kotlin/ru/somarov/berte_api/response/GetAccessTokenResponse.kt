package ru.somarov.berte_api.response

data class GetAccessTokenResponse(val access: String?, val refresh: String?, val remember: String?)
