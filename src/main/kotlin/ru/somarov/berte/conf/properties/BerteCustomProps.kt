package ru.somarov.berte.conf.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import ru.somarov.berte.conf.properties.dto.security.SecurityProps

@ConstructorBinding
@ConfigurationProperties("berte")
data class BerteCustomProps(val security: SecurityProps)
