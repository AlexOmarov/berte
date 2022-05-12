package ru.somarov.berte.business.dto

import ru.somarov.berte.domain.dto.AuthorizationStatus

data class AuthorizationResult(val status: AuthorizationStatus, val access: String?, val refresh: String?,
                               val rememberMe: String?, val id: String?) {
    constructor(status: AuthorizationStatus): this(status, null, null, null, null)
}
