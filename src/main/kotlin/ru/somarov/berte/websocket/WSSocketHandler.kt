package ru.somarov.berte.websocket

import com.caucho.hessian.io.Hessian2Input
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.io.ByteArrayInputStream

class WSSocketHandler: WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.receive().then()
    }
}