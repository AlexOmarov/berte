package ru.somarov.berte.game.config

import io.rsocket.core.RSocketConnector
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.rsocket.RSocketSecurity
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.AuthorizePayloadsSpec
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder
import reactor.util.retry.Retry
import ru.somarov.berte.common.constant.Constants
import ru.somarov.berte.common.hessian.impl.HessianDecoder
import ru.somarov.berte.common.hessian.impl.HessianEncoder
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

    @Value("\${app.rsocket.requester.uri}")
    private lateinit var uri: String

    @Bean
    fun  messageHandler(): RSocketMessageHandler {
        val handler = RSocketMessageHandler()
        handler.rSocketStrategies = RSocketStrategies.builder()
            .encoders { it.add(HessianEncoder()); it.add(SimpleAuthenticationEncoder()) }
            .decoders { it.add(HessianDecoder()) }
            .build()
        handler.argumentResolverConfigurer.addCustomResolver(AuthenticationPrincipalArgumentResolver())
        return handler
    }

    @Bean
    fun authorization(security: RSocketSecurity): PayloadSocketAcceptorInterceptor {
        security.authorizePayload { authorize: AuthorizePayloadsSpec ->
            authorize
                .anyRequest().authenticated()
                .anyExchange().permitAll()
        }.simpleAuthentication(Customizer.withDefaults())
        return security.build()
    }

    @Bean
    fun rSocketRequester(): RSocketRequester {
        val builder = RSocketRequester.builder()
        return builder
            .rsocketConnector { rSocketConnector: RSocketConnector ->
                rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2)))
            }
            .dataMimeType(Constants.HESSIAN_MIME_TYPE)
            .rsocketStrategies(RSocketStrategies.builder()
                .encoders { it.add(HessianEncoder()); it.add(SimpleAuthenticationEncoder()) }
                .decoders { it.add(HessianDecoder()) }
                .build())
            .websocket(URI.create(uri))
    }
}