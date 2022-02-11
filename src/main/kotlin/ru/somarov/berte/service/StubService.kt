package ru.somarov.berte.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StubService {
    private val logger = LoggerFactory.getLogger(StubService::class.java)

    fun executeBusinessLogic() {
        checkInline("Hey programmer") { name: String, message: String -> "$name$message" }
        checkInline("My lord") { name: String, message: String -> "$name, we a strongly recommend to begin $message" }
    }

    private inline fun checkInline(s: String, assembleMessage: (String, String) -> String) {
        logger.info(assembleMessage(s, ", executing business logic..."))
    }
}