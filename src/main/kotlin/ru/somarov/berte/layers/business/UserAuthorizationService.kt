package ru.somarov.berte.layers.business

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.layers.domain.AuthService
import ru.somarov.berte.layers.domain.BerteTokenService
import ru.somarov.berte.layers.dto.AuthorizationResult
import ru.somarov.berte.layers.dto.AuthorizedTokenHolder
import ru.somarov.berte.layers.dto.BertePublicKey
import ru.somarov.berte_api.constant.Provider

@Service
class UserAuthorizationService(
    private val authService: AuthService,
    private val berteTokenService: BerteTokenService
) {

    fun authorize(
        login: String,
        password: String,
        codeChallenge: String,
        provider: Provider,
        clientId: String,
        oidc: Boolean
    ): Mono<String> {
        return authService.authorize(login, password, codeChallenge, provider, clientId, oidc).map { it.code }
    }

    fun getPublicJwk(alias: String, encoding: String): Mono<BertePublicKey> {
        return berteTokenService.getKey(alias, encoding).map {
            BertePublicKey(
                "",
                "",
                "",
                it.toString()
            )
        }
    }

    fun token(code: String, clientId: String, verifier: String): Mono<AuthorizationResult> {
        return authService.getTokens(code, clientId, verifier)
    }

    fun revoke(revocations: String?, refresh: String?, rememberMe: String?, id: String?): Mono<Void> {
        return Mono.empty()
    }

    fun logout(id: String?, access: String, refresh: String, rememberMe: String?): Mono<Void> {
        return Mono.empty()
    }

    fun refresh(token: String): Mono<AuthorizedTokenHolder> {
        return Mono.just(AuthorizedTokenHolder("", "", "", ""))
    }
}