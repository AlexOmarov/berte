package ru.somarov.berte.auth.consumer.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.somarov.auth.constant.BerteKeyType
import ru.somarov.auth.dto.BerteKey
import ru.somarov.auth.request.*
import ru.somarov.auth.response.*
import ru.somarov.berte.auth.business.service.AuthBusinessService
import ru.somarov.berte.auth.conf.constants.Provider

@RestController
@RequestMapping("/oauth2")
class OAuth2Controller(private val authService: AuthBusinessService) {

    @PostMapping("/code", consumes = ["application/json"], produces = ["application/json"])
    fun code(
        @RequestBody request: Oauth2CodeRequest
    ): Mono<ResponseEntity<Oauth2CodeResponse>> {
        return authService.login(
            request.login,
            request.secret,
            request.codeChallenge,
            Provider.valueOf(request.provider.name)
        ).map {
            ResponseEntity.ok(Oauth2CodeResponse(it))
        }
    }

    @PostMapping("/token")
    fun token(
        @RequestBody request: TokenRequest
    ): Mono<ResponseEntity<TokenResponse>> {
        return authService.token(request.code, request.clientId, request.codeVerifier).map {
            ResponseEntity.ok(TokenResponse(it.access, it.refresh, it.rememberMe, it.id))
        }
    }

    @PostMapping("/logout")
    fun logout(
        @RequestBody request: LogoutRequest,
        @AuthenticationPrincipal user: User
    ): Mono<ResponseEntity<LogoutResponse>> {
        return authService.logout(request.id, request.access, request.refresh, request.rememberMe).map {
            ResponseEntity.ok(LogoutResponse("ok"))
        }
    }

    @PostMapping("/token/revoke")
    fun revoke(
        @RequestBody request: RevokeRequest,
        @AuthenticationPrincipal user: User
    ): Mono<ResponseEntity<RevokeResponse>> {
        return authService.revoke(request.revocations).map {
            ResponseEntity.ok(RevokeResponse("ok"))
        }
    }

    @GetMapping("/jwks")
    fun jwks(
        @RequestBody request: KeysRequest
    ): Mono<ResponseEntity<KeysResponse>> {
        return authService.getPublicJwk().map {
            ResponseEntity.ok(KeysResponse(listOf(BerteKey(BerteKeyType.JWS, it.kty, it.use, it.kid, it.n))))
        }
    }
}