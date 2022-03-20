package ru.somarov.berte.controller

import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.somarov.berte.domain.entity.User
import ru.somarov.berte.service.UserService
import ru.somarov.dto.SimpleMessage
import java.util.*


@Controller
class UserController(private val userService: UserService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("user")
    @ResponseBody
    fun register(@RequestBody email: String): Mono<User> {
        return userService.register(email)
    }

    @MessageMapping("main.{dest}")
    fun main(@DestinationVariable dest: String, @Payload message: SimpleMessage): Flux<SimpleMessage> {
        return Flux.just(SimpleMessage("Rsocket response for dest $dest and request : ${message.id}!", UUID.randomUUID()))
    }

    @GetMapping("user/{id}", "application/json")
    @ResponseBody
    fun get(@PathVariable id: UUID): Mono<User> {
        return userService.get(id)
    }
}