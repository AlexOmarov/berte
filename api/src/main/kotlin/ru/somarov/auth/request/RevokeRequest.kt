package ru.somarov.auth.request

import ru.somarov.auth.dto.Revocation

data class RevokeRequest(val revocations: List<Revocation>)
