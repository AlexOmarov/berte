package ru.somarov.berte.core.hessian.impl

import com.caucho.hessian.io.HessianSerializerOutput
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import ru.somarov.berte_api.message.SimpleMessage
import java.util.*

class HessianDecoderTests {

    private val decoder = HessianDecoder()

    @Test
    fun `Decoder decodes incoming data buffer into message`() {
        val buffer = DefaultDataBufferFactory().allocateBuffer()
        val outStr = buffer.asOutputStream()
        val output = HessianSerializerOutput(outStr)
        val uuid = UUID.randomUUID()
        val value = "TEST"
        val message = SimpleMessage(value, uuid)
        output.startMessage()
        output.writeObject(message)
        output.completeMessage()

        outStr.close()
        output.close()
        val result = decoder.decode(SimpleMessage::class.java, buffer)
        Assertions.assertTrue(result.value == value && result.id == uuid)
    }
}