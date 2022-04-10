package ru.somarov.berte.hessian

import com.caucho.hessian.io.HessianSerializerInput
import com.caucho.hessian.io.HessianSerializerOutput
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import ru.somarov.berte.common.hessian.impl.HessianDecoder
import ru.somarov.berte.common.hessian.impl.HessianEncoder
import ru.somarov.game.dto.SimpleMessage
import java.util.*

class HessianEncoderTests {

    @Test
    fun `Encoder encodes incoming data into data buffer`() {
        val encoder = HessianEncoder()
        val uuid = UUID.randomUUID()
        val value = "TEST"
        val message = SimpleMessage(value, uuid)
        val buffer = encoder.encode(message,DefaultDataBufferFactory())


        val inpStr = buffer.asInputStream()
        val hessianSerializerInput = HessianSerializerInput(inpStr)

        hessianSerializerInput.startMessage()
        val result: SimpleMessage = hessianSerializerInput.readObject() as SimpleMessage
        hessianSerializerInput.completeMessage()

        hessianSerializerInput.close()
        inpStr.close()

        DataBufferUtils.release(buffer)

        Assertions.assertTrue(result.value == value && result.id == uuid)
    }
}