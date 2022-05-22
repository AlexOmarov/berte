package ru.somarov.berte_api.response

import ru.somarov.berte_api.standard.BerteResponse
import ru.somarov.berte_api.standard.ResponseInfo
import ru.somarov.berte_api.standard.ResultCode
import java.io.Serializable

data class RefreshResponse(
    val access: String?,
    val refresh: String?,
    override val responseInfo: ResponseInfo
) : BerteResponse(), Serializable {
    constructor(access: String?, refresh: String?) : this(access, refresh, ResponseInfo.get(ResultCode.OK))
}
