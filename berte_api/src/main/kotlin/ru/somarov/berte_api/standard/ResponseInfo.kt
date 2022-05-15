package ru.somarov.berte_api.standard

data class ResponseInfo(val resultCode: ResultCode, val message: String?, val systemMessage: String?) {
    companion object {
        fun get(resultCode: ResultCode): ResponseInfo {
            return ResponseInfo(resultCode, null, null)
        }
    }
}
