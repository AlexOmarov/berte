package ru.somarov.berte.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.Channels
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlowDefinition
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler
import org.springframework.integration.mqtt.support.MqttHeaderMapper
import org.springframework.messaging.converter.SmartMessageConverter
import ru.somarov.berte.hessian.HessianMessageConverter

@Configuration
@ConditionalOnProperty(name = ["app.mqtt.enable"], havingValue = "true", matchIfMissing = false)
class MqttConfig(
    @Value("{app.mqtt.url}") val url: String,
    @Value("{app.mqtt.inClientId}") val inClientId: String,
    @Value("{app.mqtt.outClientId}") val outClientId: String,
    @Value("{app.mqtt.inTopic}") val inTopic: String,
    @Value("{app.mqtt.fromMqttChannelId}") val fromMqttChannelId: String,
    @Value("#{app.mqtt.outboundHeaders}.split(',')") val outboundHeaders: Array<String>
) {

    @Bean
    fun mqttInbound(): IntegrationFlow {
        val messageProducer = Mqttv5PahoMessageDrivenChannelAdapter(url, inClientId, inTopic).also {
            it.setPayloadType(String::class.java)
            it.setMessageConverter(mqtConverter())
            it.setManualAcks(true)
        }

        return IntegrationFlows.from(messageProducer).channel { c: Channels -> c.queue(fromMqttChannelId) }.get()
    }

    @Bean
    fun mqttOutboundFlow(): IntegrationFlow {
        val mqttHeaderMapper = MqttHeaderMapper().also { it.setOutboundHeaderNames(*outboundHeaders) }
        val messageHandler = Mqttv5PahoMessageHandler(url, outClientId).also {
            it.setHeaderMapper(mqttHeaderMapper)
            it.setAsync(true)
            it.setAsyncEvents(true)
            it.setConverter(mqtConverter())
        }

        return IntegrationFlow { f: IntegrationFlowDefinition<*> -> f.handle(messageHandler) }
    }

    @Bean
    fun mqtConverter(): SmartMessageConverter {
        return HessianMessageConverter()
    }
}