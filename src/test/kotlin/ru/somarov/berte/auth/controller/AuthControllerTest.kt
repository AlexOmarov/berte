package ru.somarov.berte.auth.controller

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.Payload
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jwt.JWTClaimsSet
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import ru.somarov.auth.request.KeysRequest
import ru.somarov.berte.auth.conf.config.SecurityConfig
import ru.somarov.berte.auth.conf.properties.AppProps
import ru.somarov.berte.auth.consumer.controller.AuthController
import ru.somarov.berte.auth.domain.service.auth.AuthService
import ru.somarov.berte.auth.domain.service.jwt.JwtService
import ru.somarov.berte.common.constant.Constants.AUTH_HEADER
import ru.somarov.berte.common.constant.Constants.HESSIAN_MIME_TYPE
import ru.somarov.berte.common.hessian.impl.HessianReader
import ru.somarov.berte.common.hessian.impl.HessianWriter
import ru.somarov.berte.game.config.RSocketConfig
import ru.somarov.berte.game.service.RSocketService
import java.security.PublicKey
import java.util.*

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class, MockKExtension::class)
@WebFluxTest(properties = ["app.scheduling.enabled=false"], controllers = [AuthController::class])
@EnableConfigurationProperties(AppProps::class)
@Import(SecurityConfig::class, RSocketConfig::class)
@AutoConfigureWebTestClient
class AuthControllerTest {

    @MockkBean
    private lateinit var authService: AuthService

    @MockkBean
    private lateinit var jwtService: JwtService

    @MockkBean
    private lateinit var rSocketService: RSocketService

    @Autowired
    private lateinit var appProps: AppProps

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `Authenticated request passes and valid response is returned`() {
        every { jwtService.public() } returns Mono.just(appProps.security.jwt.keys.public)
        val token = token("user", appProps)
        val request = KeysRequest("alias", "encoding")
        val exchange = webClient.post()
            .uri("/auth/jwk")
            .contentType(MediaType.APPLICATION_JSON)
            .header(AUTH_HEADER, "bearer $token")
            .body(Mono.just(request), KeysRequest::class.java)
            .exchange()
        exchange.expectStatus().is2xxSuccessful
    }

    private fun token(name: String, props: AppProps): String {
        val claims = JWTClaimsSet.Builder()
            .issueTime(Date())
            .expirationTime(Date(Date().time + 30000))
            .subject(name)
            .claim("scope", "PLAYER")
            .build()
        val payload = Payload(claims.toJSONObject())
        val header = JWSHeader(JWSAlgorithm.RS512)
        val token = JWSObject(header, payload)
        token.sign(RSASSASigner(props.security.jwt.keys.private))
        return token.serialize(false)
    }
}