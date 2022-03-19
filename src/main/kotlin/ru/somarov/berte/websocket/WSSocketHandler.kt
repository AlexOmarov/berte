package ru.somarov.berte.websocket

import com.caucho.hessian.io.Hessian2Input
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.somarov.dto.SimpleMessage


@Component
class WSSocketHandler : WebSocketHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun handle(session: WebSocketSession): Mono<Void> {
        val flux: Flux<WebSocketMessage> = session.receive()
            .doOnNext {
                val inpstrm = it.payload.asInputStream()
                val hessian = Hessian2Input(inpstrm)
                hessian.startMessage()
                val message: SimpleMessage = hessian.readObject(SimpleMessage::class.java) as SimpleMessage
                hessian.completeMessage()
                log.info(message.value)
                inpstrm.close()
                hessian.close()
            }

        return session.send(Flux.from(flux))
    }
}