package ru.somarov.berte.layers.persistence

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ru.somarov.berte.layers.persistence.dao.TokenDao
import ru.somarov.berte.layers.persistence.dao.UserDao
import ru.somarov.berte.layers.persistence.entity.RefreshTokenEntity
import java.util.*

@Component
class PersistenceFacade(
    private val tokenDao: TokenDao,
    private val userDao: UserDao
) {
    fun saveRefresh(refresh: UUID, userId: UUID): Mono<RefreshTokenEntity> {
        return tokenDao.refresh(refresh, userId)
    }
}
