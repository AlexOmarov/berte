package ru.somarov.berte.domain.repo

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import ru.somarov.berte.domain.entity.User
import java.util.*

interface UserRepo : ReactiveCrudRepository<User, UUID> {
    fun findByEmail(email:String): Mono<User>
}