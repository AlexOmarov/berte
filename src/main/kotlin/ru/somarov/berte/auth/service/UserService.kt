package ru.somarov.berte.auth.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.somarov.auth.dto.LoginType
import ru.somarov.auth.response.LoginResponse
import ru.somarov.berte.auth.domain.entity.User
import ru.somarov.berte.auth.domain.repo.Dao
import ru.somarov.berte.auth.service.dto.LoginResult
import java.util.*

/**
 * Service for handling interactions with user
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author alexandr.omarov
 *
 */

@Service
class UserService(private val dao: Dao) {

    fun get(email: String): Mono<User> {
        return dao.getUser(email)
    }

    fun get(id:UUID): Mono<User> {
        return dao.getUser(id)
    }

    fun login(login: String, password: String?, type: LoginType): LoginResult {
        TODO("Not yet implemented")
    }
}