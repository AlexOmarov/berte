package ru.somarov.berte.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.*
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler
import org.springframework.integration.mqtt.support.MqttHeaderMapper
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.converter.SmartMessageConverter
import ru.somarov.berte.hessian.HessianMessageConverter


@Configuration
class MqttConfig {
    
    val url: String = "URL"

    @Bean
    fun mqttInbound(): IntegrationFlow {

        val messageProducer = Mqttv5PahoMessageDrivenChannelAdapter(url, "mqttv5SIin", "siTest")
        messageProducer.setPayloadType(String::class.java)
        messageProducer.setMessageConverter(mqtConverter())
        messageProducer.setManualAcks(true)

        return IntegrationFlows.from(messageProducer)
            .channel { c: Channels -> c.queue("fromMqttChannel") }
            .get()
    }

    @Bean
    fun mqttOutboundFlow(): IntegrationFlow {
        val messageHandler = Mqttv5PahoMessageHandler(url, "mqttv5SIout")
        val mqttHeaderMapper = MqttHeaderMapper()
        mqttHeaderMapper.setOutboundHeaderNames("some_user_header", MessageHeaders.CONTENT_TYPE)
        messageHandler.setHeaderMapper(mqttHeaderMapper)
        messageHandler.setAsync(true)
        messageHandler.setAsyncEvents(true)
        messageHandler.setConverter(mqtConverter())

        return IntegrationFlow { f: IntegrationFlowDefinition<*> ->
            f.handle(messageHandler)
        }
    }

    private fun mqtConverter(): SmartMessageConverter {
        return HessianMessageConverter()
    }
}