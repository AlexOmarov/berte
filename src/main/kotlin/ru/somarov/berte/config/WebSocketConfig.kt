package ru.somarov.berte.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import org.springframework.web.reactive.socket.client.WebSocketClient
import ru.somarov.berte.websocket.WSSocketHandler


@Configuration
@ConditionalOnProperty(name = ["app.websocket.enable"], havingValue = "true", matchIfMissing = false)
class WebSocketConfig(@Value("#{'\${app.websocket.paths}'.split(',')}") val paths: Array<String>) {

    @Bean
    fun webSocketClient(): WebSocketClient {
        return ReactorNettyWebSocketClient()
    }

    @Bean
    fun webSocketHandlerMapping(handler: WSSocketHandler): HandlerMapping {
        return SimpleUrlHandlerMapping().also {
            it.order = 1
            it.urlMap = HashMap<String, WSSocketHandler>().also { paths.forEach { path -> it[path] = handler } }
        }
    }

}