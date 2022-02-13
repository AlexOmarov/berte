package ru.somarov.berte.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.berte.domain.entity.User
import ru.somarov.berte.domain.repo.Dao
import java.util.*

@Service
class UserService(private val dao: Dao) {

    fun register(email: String): Mono<User> {
        return dao.saveUser(User(UUID.randomUUID(), email))
    }

    fun get(email: String): Mono<User> {
        return dao.getUser(email)
    }

    fun get(id:UUID): Mono<User> {
        return dao.getUser(id)
    }
}