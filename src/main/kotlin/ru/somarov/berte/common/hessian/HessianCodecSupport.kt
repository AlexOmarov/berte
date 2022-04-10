package ru.somarov.berte.common.hessian

import com.caucho.hessian.io.HessianSerializerInput
import com.caucho.hessian.io.HessianSerializerOutput
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import ru.somarov.berte.common.constant.Constants.HESSIAN_MIME_TYPE

abstract class HessianCodecSupport {

     fun <T> decode(clazz: Class<T>, dataBuffer: DataBuffer): T {
        val inpStr = dataBuffer.asInputStream()
        val message = dec(clazz, HessianSerializerInput(inpStr))
        inpStr.close()
        DataBufferUtils.release(dataBuffer)
         return message
    }

    fun <T> decode(clazz: Class<T>, inputMessage: HttpInputMessage): T {
        val inpStr = inputMessage.body
        val message = dec(clazz, HessianSerializerInput(inpStr))
        inpStr.close()
        return message
    }

    fun encode(obj: Any, bufferFactory: DataBufferFactory): DataBuffer {
        val dataBuffer = bufferFactory.allocateBuffer()
        val outStr = dataBuffer.asOutputStream()
        enc(obj, HessianSerializerOutput(outStr))
        outStr.close()
        return dataBuffer
    }

    fun encode(obj: Any, outputMessage: HttpOutputMessage) {
        enc(obj, HessianSerializerOutput(outputMessage.body))
    }

    private fun enc(obj: Any, out: HessianSerializerOutput) {
        out.also { it.startMessage(); it.writeObject(obj); it.completeMessage(); it.close()}
    }

    private fun <T> dec(clazz: Class<T>, inp: HessianSerializerInput): T {
        inp.startMessage()
        val message = inp.readObject()
        inp.completeMessage()
        inp.close()
        return clazz.cast(message)
    }

    companion object {
        var HESSIAN_MEDIA_TYPES = mutableListOf(MediaType.asMediaType(HESSIAN_MIME_TYPE))
        var HESSIAN_MIME_TYPES = mutableListOf(HESSIAN_MIME_TYPE)
    }
}