package ru.somarov.berte.config

import io.rsocket.core.RSocketConnector
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.rsocket.RSocketSecurity
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.AuthorizePayloadsSpec
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder
import org.springframework.util.MimeType
import reactor.util.retry.Retry
import ru.somarov.berte.hessian.impl.HessianDecoder
import ru.somarov.berte.hessian.impl.HessianEncoder
import java.net.URI
import java.time.Duration


/**
 * Configuration of RSocket
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author alexandr.omarov
 *
 */
@Configuration
class RSocketConfig {

    @Bean
    fun  messageHandler(): RSocketMessageHandler {
        val handler = RSocketMessageHandler()
        handler.rSocketStrategies = RSocketStrategies.builder()
            .encoders { it.add(HessianEncoder()); it.add(SimpleAuthenticationEncoder()) }
            .decoders { it.add(HessianDecoder()) }
            .build()
        return handler
    }

    @Bean
    fun authorization(security: RSocketSecurity): PayloadSocketAcceptorInterceptor {
        security.authorizePayload { authorize: AuthorizePayloadsSpec ->
            authorize
                .setup().hasRole("USER")
                .anyRequest()
                .authenticated()
        }.simpleAuthentication(Customizer.withDefaults())
        return security.build()
    }
/*
    @Bean
    fun springSecurityRSocketSecurity(interceptor: SecuritySocketAcceptorInterceptor): RSocketServerCustomizer {
        return RSocketServerCustomizer { server ->
            server.interceptors { registry ->
                registry.forSocketAcceptor(interceptor)
            }
        }
    }*/

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
            .rsocketStrategies(RSocketStrategies.builder()
                .encoders { it.add(HessianEncoder()); it.add(SimpleAuthenticationEncoder()) }
                .decoders { it.add(HessianDecoder()) }
                .build())
            .websocket(URI.create("http://localhost:7000/rsocket"))
    }
}