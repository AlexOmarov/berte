package ru.somarov.berte.game.controller

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import ru.somarov.berte.auth.domain.entity.User
import ru.somarov.berte.game.service.RSocketService
import ru.somarov.game.dto.SimpleMessage

@Controller
class RSocketController(val service: RSocketService) {

    @MessageMapping("main.{dest}")
    fun main(@DestinationVariable dest: String, @Payload message: SimpleMessage,
             @AuthenticationPrincipal user: User
    ): Flux<SimpleMessage> {
        return service.doWork(message, dest, user)
    }
}