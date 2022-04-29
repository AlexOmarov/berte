package ru.somarov.berte.common.constant

import io.rsocket.metadata.WellKnownMimeType
import org.springframework.util.MimeType
import org.springframework.util.MimeTypeUtils

object Constants {
    val HESSIAN_MIME_TYPE = MimeType("application", "x-hessian")
    val RSOCKET_AUTHENTICATION_MIME_TYPE = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.string)
    val AUTH_HEADER = "Authorization"
}