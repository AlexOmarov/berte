package ru.somarov.berte.auth.config

import org.springframework.boot.web.codec.CodecCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import ru.somarov.berte.common.hessian.impl.HessianReader
import ru.somarov.berte.common.hessian.impl.HessianWriter


@Configuration
class WebFluxConfig: WebFluxConfigurer {
    @Bean
    fun hessianCodec(): CodecCustomizer {
        return CodecCustomizer {
            it.readers.add(HessianReader())
            it.writers.add(HessianWriter())
        }
    }
}