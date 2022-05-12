package ru.somarov.berte.consumer.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.somarov.berte.business.service.BusinessService
import ru.somarov.berte_api.constant.BerteKeyType
import ru.somarov.berte_api.dto.BerteKey
import ru.somarov.berte_api.request.*
import ru.somarov.berte_api.response.*

@RestController
@RequestMapping("/oauth2")
class OAuth2Controller(private val authService: BusinessService) {

    @PostMapping("/authorize", consumes = ["application/json"], produces = ["application/json"])
    fun authorize(@RequestBody request: Oauth2CodeRequest): Mono<ResponseEntity<Oauth2CodeResponse>> {
        return authService.authorize(request.login, request.secret, request.codeChallenge, request.provider, request.clientId, true)
            .map { ResponseEntity.ok(Oauth2CodeResponse(it)) }
    }

    @PostMapping("/token", consumes = ["application/json"], produces = ["application/json"])
    fun token(@RequestBody request: TokenRequest): Mono<ResponseEntity<TokenResponse>> {
        return authService.token(request.code, request.clientId, request.codeVerifier).map {
            ResponseEntity.ok(TokenResponse(it.access, it.refresh, it.rememberMe, it.id))
        }
    }

    @PostMapping("/logout", consumes = ["application/json"], produces = ["application/json"])
    fun logout(@RequestBody request: LogoutRequest, @AuthenticationPrincipal user: User): Mono<ResponseEntity<LogoutResponse>> {
        return authService.logout(request.id, request.access, request.refresh, request.rememberMe).map {
            ResponseEntity.ok(LogoutResponse("ok"))
        }
    }

    @PostMapping("/token/revoke", consumes = ["application/json"], produces = ["application/json"])
    fun revoke(@RequestBody request: RevokeRequest, @AuthenticationPrincipal user: User): Mono<ResponseEntity<RevokeResponse>> {
        return authService.revoke(request.access, request.refresh, request.rememberMe, request.id).map {
            ResponseEntity.ok(RevokeResponse("ok"))
        }
    }

    @GetMapping("/jwks", consumes = ["application/json"], produces = ["application/json"])
    fun jwks(@RequestBody request: KeysRequest): Mono<ResponseEntity<KeysResponse>> {
        return authService.getPublicJwk(request.alias, request.encoding).map {
            ResponseEntity.ok(KeysResponse(listOf(BerteKey(BerteKeyType.JWS, it.kty, it.use, it.kid, it.n))))
        }
    }
}