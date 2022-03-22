package ru.somarov.berte.controller

import com.ninjasquad.springmockk.MockkBean
import io.rsocket.core.RSocketConnector
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.MimeType
import reactor.core.scheduler.Schedulers
import reactor.util.retry.Retry
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
class RSocketControllerTests {

    @MockkBean
    private lateinit var service: RSocketService

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
                .encoders { it.add(HessianEncoder()) }
                .decoders { it.add(HessianDecoder()) }
                .build())
            .websocket(URI.create("http://localhost:7000/rsocket"))


        rSocketRequester
            .route("main.${Random.nextInt()}")
            .data(SimpleMessage("Rsocket request", UUID.randomUUID()))
            .retrieveFlux(SimpleMessage::class.java)
            .doOnNext {
                log.info(it.value)
            }
            .subscribeOn(Schedulers.parallel())
            .subscribe()
    }
}