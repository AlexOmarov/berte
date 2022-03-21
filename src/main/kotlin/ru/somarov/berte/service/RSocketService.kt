package ru.somarov.berte.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import ru.somarov.dto.SimpleMessage
import java.util.*

/**
 * Service for handling RSocket exchange
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author alexandr.omarov
 *
 */
@Service
class RSocketService {
    fun doWork(message: SimpleMessage, dest: String): Flux<SimpleMessage> {
        return Flux.just(SimpleMessage("Rsocket response for dest $dest and request : ${message.id}!", UUID.randomUUID()))
    }
}