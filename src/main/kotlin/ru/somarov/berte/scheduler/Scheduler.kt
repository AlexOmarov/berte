package ru.somarov.berte.scheduler

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata
import org.springframework.stereotype.Component
import org.springframework.util.MimeTypeUtils
import reactor.core.scheduler.Schedulers
import ru.somarov.dto.SimpleMessage
import java.util.*
import kotlin.random.Random


@Component
@ConditionalOnProperty(name = ["app.scheduling.enabled"], havingValue = "true", matchIfMissing = false)
class Scheduler(val rSocketRequester: RSocketRequester) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${app.user}")
    private lateinit var user: String

    @Autowired
    private lateinit var encoder: PasswordEncoder

    @Scheduled(fixedDelay = 1000)
    fun rsocket() {
        val credentials = UsernamePasswordMetadata(user, "password")
        rSocketRequester
            .route("main.${Random.nextInt()}")
            .metadata(credentials, MimeTypeUtils.parseMimeType("message/x.rsocket.authentication.v0"))
            .data(SimpleMessage("Rsocket request", UUID.randomUUID()))
            .retrieveFlux(SimpleMessage::class.java)
            .doOnNext {
                log.info(it.value)
            }
            .subscribeOn(Schedulers.parallel())
            .subscribe()
    }
}