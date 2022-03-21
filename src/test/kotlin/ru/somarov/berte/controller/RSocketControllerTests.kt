package ru.somarov.berte.controller

import org.springframework.messaging.rsocket.RSocketRequester
import reactor.util.retry.Retry
import java.time.Duration

class RSocketControllerTests {
/*    val builder = RSocketRequester.builder()
    return builder
    .rsocketConnector { rSocketConnector: RSocketConnector ->
        rSocketConnector.reconnect(
            Retry.fixedDelay(
                2,
                Duration.ofSeconds(2)
            )
        )
    }
    .dataMimeType(MimeType("application", "x-hessian"))
    .rsocketStrategies(strategies())
    .websocket(URI.create("http://localhost:7000/rsocket"))


    rSocketRequester
    .route("main.${Random.nextInt()}")
    .data(SimpleMessage("Rsocket request", UUID.randomUUID()))
    .retrieveFlux(SimpleMessage::class.java)
    .doOnNext {
        log.info(it.value)
    }
    .subscribeOn(Schedulers.parallel())
    .subscribe()*/
}