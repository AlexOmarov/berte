package ru.somarov.berte.scheduler

import com.caucho.hessian.io.Hessian2Output
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.client.WebSocketClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import ru.somarov.dto.SimpleMessage
import java.io.ByteArrayOutputStream
import java.net.URI
import java.time.Duration
import java.util.*
import kotlin.random.Random


@Component
class Scheduler(val webSocketClient: WebSocketClient, val rSocketRequester: RSocketRequester,
                @Value("#{'\${app.websocket.paths}'.split(',')}") val paths: Array<String>) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelay = 10)
    fun send() {
        paths.forEach { path ->
            webSocketClient.execute(URI.create("ws://localhost:8080$path"), ::handler).block(Duration.ofSeconds(2))
        }
    }

    @Scheduled(fixedDelay = 10)
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

    private fun handler(session: WebSocketSession): Mono<Void> {
        return session.send(Mono.just(
            session.binaryMessage {
                it.allocateBuffer().write(getBytes(SimpleMessage("WebSocket request", UUID.randomUUID())))
            }
        ))
    }

    private fun getBytes(obj: Any): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val hessian2Output = Hessian2Output(byteArrayOutputStream)
        hessian2Output.startMessage()
        hessian2Output.writeObject(obj)
        hessian2Output.completeMessage()
        byteArrayOutputStream.close()
        hessian2Output.close()
        return byteArrayOutputStream.toByteArray()
    }
}