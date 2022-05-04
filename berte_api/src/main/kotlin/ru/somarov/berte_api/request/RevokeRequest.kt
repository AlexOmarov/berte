package ru.somarov.berte_api.request

import ru.somarov.berte_api.dto.Revocation

data class RevokeRequest(val revocations: List<Revocation>)
