package ru.somarov.berte.config

import io.rsocket.core.RSocketConnector
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
import org.springframework.util.MimeType
import reactor.util.retry.Retry
import ru.somarov.berte.hessian.HessianDecoder
import ru.somarov.berte.hessian.HessianEncoder
import java.net.URI
import java.time.Duration

@Configuration
class RsocketConfig {

    @Bean
    fun  messageHandler(strategies: RSocketStrategies): RSocketMessageHandler {
        val handler = RSocketMessageHandler()
        handler.rSocketStrategies = strategies
        return handler
    }

    @Bean
    fun strategies(): RSocketStrategies {
        return RSocketStrategies.builder()
            .encoders { it.add(HessianEncoder()) }
            .decoders { it.add(HessianDecoder()) }
            .build()
    }

    @Bean
    fun rSocketRequester(): RSocketRequester {
        val builder = RSocketRequester.builder()
        return builder
            .rsocketConnector { rSocketConnector: RSocketConnector ->
                rSocketConnector.reconnect(
                    Retry.fixedDelay(
                        2,
                        Duration.ofSeconds(2)
                    )
                )
            }
            .dataMimeType(MimeType("application", "x-hessian"))
            .rsocketStrategies(strategies())
            .websocket(URI.create("http://localhost:7000/rsocket"))
    }
}