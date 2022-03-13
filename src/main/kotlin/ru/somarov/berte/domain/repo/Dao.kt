package ru.somarov.berte.domain.repo

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ru.somarov.berte.domain.entity.User
import java.util.UUID

@Component
class Dao(private val userRepo: UserRepo) {
    fun saveUser(user: User) {
        userRepo.insert(user.id, user.email)
    }

    fun getUser(email: String): Mono<User> {
        return userRepo.findByEmail(email)
    }

    fun getUser(id: UUID): Mono<User> {
        return userRepo.findById(id)
    }
}