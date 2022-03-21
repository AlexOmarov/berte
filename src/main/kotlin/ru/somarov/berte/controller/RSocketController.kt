package ru.somarov.berte.controller

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import ru.somarov.dto.SimpleMessage
import java.util.*


@Controller
class RSocketController {

    @MessageMapping("main.{dest}")
    fun main(@DestinationVariable dest: String, @Payload message: SimpleMessage): Flux<SimpleMessage> {
        return Flux.just(SimpleMessage("Rsocket response for dest $dest and request : ${message.id}!", UUID.randomUUID()))
    }
}