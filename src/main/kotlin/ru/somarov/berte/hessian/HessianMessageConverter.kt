package ru.somarov.berte.hessian

import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.converter.AbstractMessageConverter
import org.springframework.util.MimeTypeUtils

class HessianMessageConverter: AbstractMessageConverter(MimeTypeUtils.APPLICATION_OCTET_STREAM) {
    override fun supports(clazz: Class<*>): Boolean {
        TODO("Not yet implemented")
    }

    override fun convertFromInternal(message: Message<*>, targetClass: Class<*>, conversionHint: Any?): Any? {
        return super.convertFromInternal(message, targetClass, conversionHint)
    }

    override fun convertToInternal(payload: Any, headers: MessageHeaders?, conversionHint: Any?): Any? {
        return super.convertToInternal(payload, headers, conversionHint)
    }
}