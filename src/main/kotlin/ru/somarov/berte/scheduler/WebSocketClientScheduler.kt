package ru.somarov.berte.scheduler

import com.caucho.hessian.io.Hessian2Output
import kotlinx.coroutines.Dispatchers
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.client.WebSocketClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import ru.somarov.dto.SimpleMessage
import java.io.ByteArrayOutputStream
import java.net.URI
import java.time.Duration


@Component
class WebSocketClientScheduler(
    val webSocketClient: WebSocketClient,
    @Value("#{'\${app.websocket.paths}'.split(',')}") val paths: Array<String>
) {

    @Scheduled(fixedDelay = 5000)
    fun send() {
        paths.forEach { path ->
            // TODO: io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1 onComplete
            webSocketClient.execute(URI.create("ws://localhost:8080$path"), ::handler).subscribeOn(Schedulers.parallel()).subscribe()
        }
    }

    private fun handler(session: WebSocketSession): Mono<Void> {
        val message: Mono<WebSocketMessage> = Mono.just(
            session.binaryMessage {
                val byteArrayOutputStream = ByteArrayOutputStream()
                val hessian2Output = Hessian2Output(byteArrayOutputStream)
                hessian2Output.startMessage()
                hessian2Output.writeObject(SimpleMessage("Hello from the depth of WebSocket exchange!"))
                hessian2Output.completeMessage()
                byteArrayOutputStream.close()
                hessian2Output.close()
                it.allocateBuffer().write(byteArrayOutputStream.toByteArray())
            }
        )

        return session.send(message)
    }
}