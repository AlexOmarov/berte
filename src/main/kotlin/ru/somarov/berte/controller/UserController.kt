package ru.somarov.berte.controller

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.somarov.berte.domain.entity.User
import ru.somarov.berte.service.UserService
import java.util.*

@RestController
@RequestMapping("user")
class UserController(private val userService: UserService) {

    @PostMapping
    fun register(@RequestBody email: String): Mono<User> {
        return userService.register(email)
    }

    @GetMapping("/{id}", "application/json")
    fun get(@PathVariable id: UUID): Mono<User> {
        return userService.get(id)
    }
}