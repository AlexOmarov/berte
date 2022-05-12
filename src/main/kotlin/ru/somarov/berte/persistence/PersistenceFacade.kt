package ru.somarov.berte.persistence

import org.springframework.stereotype.Component
import ru.somarov.berte.persistence.dao.TokenDao
import ru.somarov.berte.persistence.dao.UserDao
import java.util.*

@Component
class PersistenceFacade (
    private val invalidTokenRepo: TokenDao,
    private val resetPasswordTokenRepo: UserDao
) {
    fun saveRefresh(refresh: String, userId: UUID) {
        TODO("Not yet implemented")
    }

}