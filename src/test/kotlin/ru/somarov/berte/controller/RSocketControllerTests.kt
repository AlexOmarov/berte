package ru.somarov.berte.controller

import com.ninjasquad.springmockk.MockkBean
import io.rsocket.core.RSocketConnector
import io.rsocket.exceptions.RejectedSetupException
import io.rsocket.metadata.WellKnownMimeType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import reactor.util.retry.Retry
import ru.somarov.berte.hessian.impl.HessianDecoder
import ru.somarov.berte.hessian.impl.HessianEncoder
import ru.somarov.berte.service.RSocketService
import ru.somarov.dto.SimpleMessage
import java.net.URI
import java.time.Duration
import java.util.*
import kotlin.random.Random

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest
@Testcontainers
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

        val flux = rSocketRequester
            .route("main.${Random.nextInt()}")
            .metadata(credentials, MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.string))
            .data(SimpleMessage("Rsocket request", UUID.randomUUID()))
            .retrieveFlux(SimpleMessage::class.java)
            .doOnNext {
                log.info(it.value)
            }

        StepVerifier
            .create(flux)
            .expectNextMatches { message -> message.value.contains("Response") }
            .expectComplete()
            .verify()

        StepVerifier
            .create(flux)
            .expectErrorMatches {error -> error is RejectedSetupException }
            .verify()
    }

    companion object Obj {

        @Container
        var postgresql = PostgreSQLContainer<Nothing>("postgres:13.4").apply {
            withDatabaseName("berte")
            withUsername("berte")
            withPassword("berte")
        }

        @JvmStatic
        @DynamicPropertySource
        fun postgresProps(registry: DynamicPropertyRegistry) {
            registry.add("spring.r2dbc.url", this::r2dbcUrl)
            registry.add("spring.r2dbc.username", postgresql::getUsername)
            registry.add("spring.r2dbc.password", postgresql::getPassword)
            registry.add("spring.flyway.url", postgresql::getJdbcUrl)
            registry.add("spring.quartz.properties.org.quartz.dataSource.quartzDataSource.URL") { "${postgresql.jdbcUrl}&schema=berte" }
        }

        private fun r2dbcUrl(): String {
            return String.format("r2dbc:postgresql://%s:%s/%s", postgresql.containerIpAddress,
                postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT), postgresql.databaseName
            )
        }
    }
}