package ru.somarov.berte.game.scheduler

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.rsocket.metadata.BearerTokenMetadata
import org.springframework.stereotype.Component
import reactor.core.scheduler.Schedulers
import ru.somarov.berte.common.constant.Constants.RSOCKET_AUTHENTICATION_MIME_TYPE
import ru.somarov.game.dto.SimpleMessage
import java.util.*
import kotlin.random.Random


@Component
@ConditionalOnProperty(name = ["app.scheduling.enabled"], havingValue = "true", matchIfMissing = false)
class Scheduler(val rSocketRequester: RSocketRequester) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${app.user}")
    private lateinit var user: String

    @Scheduled(fixedDelay = 1000)
    fun socket() {
        val credentials = BearerTokenMetadata("")
        rSocketRequester
            .route("main.${Random.nextInt()}")
            .metadata(credentials, RSOCKET_AUTHENTICATION_MIME_TYPE)
            .data(SimpleMessage("Rsocket request", UUID.randomUUID()))
            .retrieveFlux(SimpleMessage::class.java)
            .doOnNext {
                log.info(it.value)
            }
            .subscribeOn(Schedulers.parallel())
            .subscribe()
    }
}