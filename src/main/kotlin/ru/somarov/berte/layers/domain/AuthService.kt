package ru.somarov.berte.layers.domain

import org.springframework.cache.CacheManager
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.layers.dto.AuthorizationCodeInfo
import ru.somarov.berte.layers.dto.AuthorizationResult
import ru.somarov.berte.layers.dto.AuthorizationStatus
import ru.somarov.berte_api.constant.Provider
import java.util.*
import kotlin.collections.HashMap

@Service
class AuthService(
    private val authManager: ReactiveAuthenticationManager,
    private val berteTokenService: BerteTokenService,
    private val cacheManager: CacheManager
) {

    private val cache = "code"

    fun authorize(username: String, password: String, codeChallenge: String, provider: Provider, clientId: String, oidc: Boolean): Mono<AuthorizationCodeInfo> {
        return authManager.authenticate(createAuthentication(username, password, provider))
            .map {
                val info = AuthorizationCodeInfo(
                    UUID.fromString((it.details as HashMap<String, String>)["id"]),
                    username, UUID.randomUUID().toString(), clientId, codeChallenge, oidc
                )
                cacheManager.getCache(cache)?.put(info.code, info)
                info
            }
    }

    fun getTokens(code: String, clientId: String, verifier: String): Mono<AuthorizationResult> {
        cacheManager.getCache(cache)?.get(code, AuthorizationCodeInfo::class.java)?.let { authorizationCodeInfo ->
            if(validate(authorizationCodeInfo.codeChallenge, verifier, clientId, authorizationCodeInfo.clientId)) {
                return berteTokenService.generateTokens(authorizationCodeInfo.username, authorizationCodeInfo.userId, authorizationCodeInfo.oidc).map {
                    AuthorizationResult(
                        AuthorizationStatus.OK,
                        it.access,
                        it.refresh,
                        it.rememberMe,
                        it.id
                    )
                }
            } else {
                return Mono.just(AuthorizationResult(AuthorizationStatus.INVALID))
            }
        }
        return Mono.just(AuthorizationResult(AuthorizationStatus.NO_CODE))
    }

    // Private methods

    private fun validate(codeChallenge: String, verifier: String, incomingClientId: String, storedClientId: String): Boolean {
        return codeChallenge == verifier && incomingClientId == storedClientId
    }

    private fun createAuthentication(username: String, password: String, type: Provider): Authentication {
        return when(type) {
            Provider.NATIVE -> UsernamePasswordAuthenticationToken(username, password)
        }
    }

}