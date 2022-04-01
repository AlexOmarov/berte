package ru.somarov.berte.controller

import com.ninjasquad.springmockk.MockkBean
import io.rsocket.core.RSocketConnector
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils
import reactor.util.retry.Retry
import ru.somarov.berte.config.RSocketConfig
import ru.somarov.berte.config.SecurityConfig
import ru.somarov.berte.hessian.impl.HessianDecoder
import ru.somarov.berte.hessian.impl.HessianEncoder
import ru.somarov.berte.service.RSocketService
import ru.somarov.dto.SimpleMessage
import java.net.URI
import java.time.Duration
import java.util.*
import kotlin.random.Random


@ExtendWith(SpringExtension::class)
@WebFluxTest
@Import(SecurityConfig::class, RSocketConfig::class)
class RSocketControllerTests {

    @MockkBean
    private lateinit var service: RSocketService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Value("\${app.user}")
    private lateinit var user: String

    private val log = LoggerFactory.getLogger(javaClass)

    @Test
    fun `test that messages API returns latest messages`() {
        val rSocketRequester = RSocketRequester.builder()
            .rsocketConnector { rSocketConnector: RSocketConnector ->
                rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2)))
            }
            .dataMimeType(MimeType("application", "x-hessian"))
            .rsocketStrategies(
                RSocketStrategies.builder()
                .encoders { it.add(HessianEncoder()); it.add(SimpleAuthenticationEncoder()) }
                .decoders { it.add(HessianDecoder()) }
                .build())
            .websocket(URI.create("http://localhost:7000/rsocket"))

        val credentials = UsernamePasswordMetadata(user, passwordEncoder.encode("password"))
        rSocketRequester
            .route("main.${Random.nextInt()}")
            .metadata(credentials, MimeTypeUtils.parseMimeType("message/x.rsocket.authentication.v0"))
            .data(SimpleMessage("Rsocket request", UUID.randomUUID()))
            .retrieveFlux(SimpleMessage::class.java)
            .doOnNext {
                log.info(it.value)
            }
            .subscribe()
    }
}