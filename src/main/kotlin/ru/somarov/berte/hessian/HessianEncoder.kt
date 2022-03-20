package ru.somarov.berte.hessian

import org.reactivestreams.Publisher
import org.springframework.core.ResolvableType
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.MediaType
import org.springframework.http.codec.HttpMessageEncoder
import org.springframework.util.MimeType
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import ru.somarov.dto.SimpleMessage


class HessianEncoder : HessianCodecSupport<SimpleMessage>(), HttpMessageEncoder<SimpleMessage> {

    override fun getStreamingMediaTypes(): List<MediaType> {
        return HESSIAN_MEDIA_TYPES
    }

    override fun canEncode(elementType: ResolvableType, mimeType: MimeType?): Boolean {
        return HESSIAN_MIME_TYPE == mimeType
    }

    override fun encode(
        inputStream: Publisher<out SimpleMessage>,
        bufferFactory: DataBufferFactory,
        elementType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Flux<DataBuffer> {
        return Flux.from(inputStream)
            .handle { obj: SimpleMessage, sink: SynchronousSink<DataBuffer> -> sink.next(encode(obj, bufferFactory)) }
    }

    override fun encodeValue(
        value: SimpleMessage,
        bufferFactory: DataBufferFactory,
        valueType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): DataBuffer {
        return encode(value, bufferFactory)
    }

    override fun getEncodableMimeTypes(): List<MimeType> {
        return HESSIAN_MIME_TYPES
    }
}