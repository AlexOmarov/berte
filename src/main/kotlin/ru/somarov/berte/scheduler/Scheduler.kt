package ru.somarov.berte.scheduler

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import reactor.core.scheduler.Schedulers
import ru.somarov.dto.SimpleMessage
import java.util.*
import kotlin.random.Random


@Component
@ConditionalOnProperty(name = ["app.scheduling.enabled"], havingValue = "true", matchIfMissing = false)
class Scheduler(val rSocketRequester: RSocketRequester) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelay = 1000)
    fun rsocket() {
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