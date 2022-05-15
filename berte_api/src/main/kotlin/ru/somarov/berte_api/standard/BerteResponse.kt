package ru.somarov.berte_api.standard

import java.io.Serializable

abstract class BerteResponse: Serializable {
    abstract val responseInfo: ResponseInfo
}
