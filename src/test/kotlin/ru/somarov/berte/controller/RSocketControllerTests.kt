package ru.somarov.berte.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.security.rsocket.metadata.BearerTokenMetadata
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier
import ru.somarov.berte.auth.service.jwt.JwtService
import ru.somarov.berte.common.constant.Constants.RSOCKET_AUTHENTICATION_MIME_TYPE
import ru.somarov.berte.game.service.RSocketService
import ru.somarov.game.dto.SimpleMessage
import java.util.*
import java.util.concurrent.CancellationException
import kotlin.random.Random


@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@Testcontainers
@SpringBootTest(properties = ["app.scheduling.enabled=false"])
class RSocketControllerTests {

    @Autowired
    private lateinit var service: RSocketService
    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var rSocketRequester: RSocketRequester

    @Value("\${berte.user}")
    private lateinit var user: String

    private val log = LoggerFactory.getLogger(javaClass)

    @Test
    fun `Authenticated request passes and valid response is returned`() {

        val credentials = BearerTokenMetadata(jwtService.jwt(user))

        val flux = rSocketRequester
            .route("main.${Random.nextInt()}")
            .metadata(credentials, RSOCKET_AUTHENTICATION_MIME_TYPE)
            .data(SimpleMessage("Rsocket request", UUID.randomUUID()))
            .retrieveFlux(SimpleMessage::class.java)
            .doOnNext {
                log.info(it.value)
            }

        StepVerifier
            .create(flux)
            .expectNextMatches { message -> message.value.contains("response") }
            .expectComplete()
            .verify()
    }

    @Test
    fun `When not authenticated then error`() {
        val flux = rSocketRequester
            .route("main.${Random.nextInt()}")
            .data(SimpleMessage("Rsocket request", UUID.randomUUID()))
            .retrieveFlux(SimpleMessage::class.java)
            .onErrorMap {
                CancellationException(it.message)
            }
            .doOnNext {
                log.info(it.value)
            }

        StepVerifier
            .create(flux)
            .verifyErrorMessage("Access Denied")
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
                postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT), postgresql.databaseName)
        }
    }
}