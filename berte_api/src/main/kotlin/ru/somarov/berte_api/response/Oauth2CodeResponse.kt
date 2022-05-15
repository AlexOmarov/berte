package ru.somarov.berte_api.response

import ru.somarov.berte_api.standard.BerteResponse
import ru.somarov.berte_api.standard.ResponseInfo
import ru.somarov.berte_api.standard.ResultCode
import java.io.Serializable

data class Oauth2CodeResponse(private val code: String, override val responseInfo: ResponseInfo): BerteResponse(), Serializable {
    constructor(code: String): this(code, ResponseInfo.get(ResultCode.OK))
}