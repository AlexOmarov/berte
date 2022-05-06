package ru.somarov.berte_api.response

import ru.somarov.berte_api.standard.BerteResponse
import ru.somarov.berte_api.standard.ResponseInfo
import ru.somarov.berte_api.standard.ResultCode
import java.io.Serializable

data class LogoutResponse(val ok: String, override val responseInfo: ResponseInfo): BerteResponse(), Serializable {
    constructor(ok: String,): this(ok, ResponseInfo.get(ResultCode.OK))
}