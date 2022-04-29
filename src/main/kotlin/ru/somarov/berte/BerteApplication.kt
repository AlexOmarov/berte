package ru.somarov.berte

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class BerteApplication

fun main(args: Array<String>) {
	runApplication<BerteApplication>(*args)
}
