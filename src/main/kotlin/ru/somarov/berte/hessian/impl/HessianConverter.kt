package ru.somarov.berte.hessian.impl

import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import ru.somarov.berte.hessian.HessianCodecSupport

class HessianConverter: HessianCodecSupport<Any>(), HttpMessageConverter<Any> {
    override fun canRead(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return mediaType != null && HESSIAN_MIME_TYPES.contains(mediaType)
    }

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return mediaType != null && HESSIAN_MIME_TYPES.contains(mediaType)
    }

    override fun getSupportedMediaTypes(): MutableList<MediaType> {
        return HESSIAN_MEDIA_TYPES
    }

    override fun read(clazz: Class<out Any>, inputMessage: HttpInputMessage): Any {
        TODO("Not yet implemented")
    }

    override fun write(t: Any, contentType: MediaType?, outputMessage: HttpOutputMessage) {

    }
}