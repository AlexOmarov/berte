package ru.somarov.berte.auth.persistence

import org.springframework.stereotype.Component
import ru.somarov.berte.auth.converter.PersistenceDomainConverter
import ru.somarov.berte.auth.persistence.dao.TokenDao
import ru.somarov.berte.auth.persistence.dao.UserDao

@Component
class PersistenceFacade (
    private val invalidTokenRepo: TokenDao,
    private val resetPasswordTokenRepo: UserDao,
    private val converter: PersistenceDomainConverter,
) {

}