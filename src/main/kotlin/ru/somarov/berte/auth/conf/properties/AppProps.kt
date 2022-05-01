package ru.somarov.berte.auth.conf.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import ru.somarov.berte.auth.conf.properties.dto.security.SecurityProps

@ConstructorBinding
@ConfigurationProperties("berte")
data class AppProps(val security: SecurityProps)
