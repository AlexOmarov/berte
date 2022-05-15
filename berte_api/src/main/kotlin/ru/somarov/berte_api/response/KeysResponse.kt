package ru.somarov.berte_api.response

import ru.somarov.berte_api.dto.BerteKey
import ru.somarov.berte_api.standard.BerteResponse
import ru.somarov.berte_api.standard.ResponseInfo
import ru.somarov.berte_api.standard.ResultCode
import java.io.Serializable

data class KeysResponse(val keys: List<BerteKey>, override val responseInfo: ResponseInfo): BerteResponse(), Serializable {
    constructor(keys: List<BerteKey>): this(keys, ResponseInfo.get(ResultCode.OK))
}
