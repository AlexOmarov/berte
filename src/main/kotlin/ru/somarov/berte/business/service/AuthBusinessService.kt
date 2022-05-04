package ru.somarov.berte.business.service

import reactor.core.publisher.Mono
import ru.somarov.berte_api.constant.Provider
import ru.somarov.berte_api.dto.Revocation

class AuthBusinessService {
    fun login(login: String, password: String, codeChallenge: String, valueOf: Provider): Mono<String> {
        TODO("Not yet implemented")
    }

    fun getPublicJwk(): Mono<ru.somarov.berte.business.dto.BertePublicKey> {
        TODO("Not yet implemented")
    }

    fun token(code: String, clientId: String, codeVerifier: String): Mono<ru.somarov.berte.business.dto.AuthorizationResult> {
        TODO("Not yet implemented")
    }

    fun revoke(revocations: List<Revocation>): Mono<Void> {
        TODO("Not yet implemented")
    }

    fun logout(id: String?, access: String, refresh: String, rememberMe: String?): Mono<Void> {
        TODO("Not yet implemented")
    }
}