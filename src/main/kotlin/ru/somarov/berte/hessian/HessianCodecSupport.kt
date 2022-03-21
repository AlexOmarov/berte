package ru.somarov.berte.hessian

import com.caucho.hessian.io.HessianSerializerInput
import com.caucho.hessian.io.HessianSerializerOutput
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.MediaType
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils


open class HessianCodecSupport<T> {

    inline fun <reified T> decode(dataBuffer: DataBuffer): T {
        val inpStr = dataBuffer.asInputStream()
        val hessianSerializerInput = HessianSerializerInput(inpStr)

        hessianSerializerInput.startMessage()
        val message: T = hessianSerializerInput.readObject(T::class.java) as T
        hessianSerializerInput.completeMessage()

        hessianSerializerInput.close()
        inpStr.close()

        DataBufferUtils.release(dataBuffer)
        return message
    }

    inline fun <reified T> encode(obj: T, bufferFactory: DataBufferFactory): DataBuffer {
        val dataBuffer = bufferFactory.allocateBuffer()
        val outStr = dataBuffer.asOutputStream()
        val output = HessianSerializerOutput(outStr)

        output.startMessage()
        output.writeObject(obj)
        output.completeMessage()

        outStr.close()
        output.close()
        return dataBuffer
    }

    companion object {
        var HESSIAN_MIME_TYPE: MimeType = MimeTypeUtils.parseMimeType("application/x-hessian")
        var HESSIAN_MEDIA_TYPES = mutableListOf(MediaType.asMediaType(HESSIAN_MIME_TYPE))
        var HESSIAN_MIME_TYPES = mutableListOf(HESSIAN_MIME_TYPE)
    }
}