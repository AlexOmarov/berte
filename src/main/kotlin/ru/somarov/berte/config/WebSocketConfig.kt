package ru.somarov.berte.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import ru.somarov.berte.websocket.WSSocketHandler


@Configuration
@ConditionalOnProperty(name = ["app.websocket.enable"], havingValue = "true", matchIfMissing = false)
class WebSocketConfig(@Value("#{'\${app.websocket.paths}'.split(',')}") val paths: Array<String>) {

    @Bean
    fun webSocketHandlerMapping(): HandlerMapping {
        val map: MutableMap<String, WSSocketHandler> = HashMap()
        paths.forEach { map[it] = handler() }
        val handlerMapping = SimpleUrlHandlerMapping()
        handlerMapping.order = 1
        handlerMapping.urlMap = map
        return handlerMapping
    }

    @Bean
    fun handler(): WSSocketHandler {
        return WSSocketHandler()
    }


}