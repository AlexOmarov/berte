package ru.somarov.berte.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StubService {
    private val logger = LoggerFactory.getLogger(StubService::class.java)

    fun executeBusinessLogic() {
        logger.info("Executing business logic...")
    }
}