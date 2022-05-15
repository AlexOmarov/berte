package ru.somarov.berte_api.response

import ru.somarov.berte_api.standard.BerteResponse
import ru.somarov.berte_api.standard.ResponseInfo
import java.io.Serializable

data class GetAccessTokenResponse(val access: String?, val refresh: String?, val remember: String?,
                                  override val responseInfo: ResponseInfo
): BerteResponse(), Serializable
