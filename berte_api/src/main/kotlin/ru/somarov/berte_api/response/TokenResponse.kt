package ru.somarov.berte_api.response

import ru.somarov.berte_api.standard.BerteResponse
import ru.somarov.berte_api.standard.ResponseInfo
import ru.somarov.berte_api.standard.ResultCode
import java.io.Serializable

data class TokenResponse(
    private val access: String?, private val refresh: String?, private val rememberMe: String?, private val id: String?,
    override val responseInfo: ResponseInfo
) : BerteResponse(), Serializable {
    constructor(access: String?, refresh: String?, rememberMe: String?, id: String?) :
            this(access, refresh, rememberMe, id, ResponseInfo.get(ResultCode.OK))
}