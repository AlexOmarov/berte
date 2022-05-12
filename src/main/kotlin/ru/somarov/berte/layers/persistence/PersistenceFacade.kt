package ru.somarov.berte.layers.persistence

import org.springframework.stereotype.Component
import ru.somarov.berte.layers.persistence.dao.TokenDao
import ru.somarov.berte.layers.persistence.dao.UserDao
import java.util.*

@Component
class PersistenceFacade (
    private val tokenDao: TokenDao,
    private val userDao: UserDao
) {
    fun saveRefresh(refresh: String, userId: UUID) {
        TODO("Not yet implemented")
    }

}