package ru.somarov.berte.auth.domain.repo

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ru.somarov.berte.auth.domain.entity.User
import java.util.UUID

@Component
class Dao(private val userRepo: UserRepo) {
    fun saveUser(user: User): Mono<User> {
        return userRepo.save(user)
    }

    fun getUser(email: String): Mono<User> {
        return userRepo.findByEmail(email)
    }

    fun getUser(id: UUID): Mono<User> {
        return userRepo.findById(id)
    }
}