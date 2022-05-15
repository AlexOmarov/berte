package ru.somarov.berte.conf

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.codec.CodecCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import ru.somarov.berte.core.hessian.impl.HessianReader
import ru.somarov.berte.core.hessian.impl.HessianWriter

@Configuration
@ConfigurationPropertiesScan
@EnableConfigurationProperties
class WebFluxConfig: WebFluxConfigurer {
    @Bean
    fun hessianCodec(): CodecCustomizer {
        return CodecCustomizer {
            it.customCodecs().register(HessianWriter())
            it.customCodecs().register(HessianReader())
        }
    }
}