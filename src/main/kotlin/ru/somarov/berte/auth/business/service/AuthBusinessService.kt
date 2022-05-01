package ru.somarov.berte.auth.business.service

import reactor.core.publisher.Mono
import ru.somarov.auth.dto.Revocation
import ru.somarov.berte.auth.business.dto.AuthorizationResult
import ru.somarov.berte.auth.business.dto.BertePublicKey
import ru.somarov.berte.auth.conf.constants.Provider

class AuthBusinessService {
    fun login(login: String, password: String, codeChallenge: String, valueOf: Provider): Mono<String> {
        TODO("Not yet implemented")
    }

    fun getPublicJwk(): Mono<BertePublicKey> {
        TODO("Not yet implemented")
    }

    fun token(code: String, clientId: String, codeVerifier: String): Mono<AuthorizationResult> {
        TODO("Not yet implemented")
    }

    fun revoke(revocations: List<Revocation>): Mono<Void> {
        TODO("Not yet implemented")
    }

    fun logout(id: String?, access: String, refresh: String, rememberMe: String?): Mono<Void> {
        TODO("Not yet implemented")
    }
}