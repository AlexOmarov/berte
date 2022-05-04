package ru.somarov.berte.consumer.controller

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import ru.somarov.berte.domain.service.RSocketService
import ru.somarov.berte_api.SimpleMessage

@Controller
class RSocketController(val service: RSocketService) {

    @MessageMapping("main.{dest}")
    fun main(@DestinationVariable dest: String, @Payload message: SimpleMessage,
             @AuthenticationPrincipal user: Jwt
    ): Flux<SimpleMessage> {
        return service.doWork(message, dest, user)
    }
}