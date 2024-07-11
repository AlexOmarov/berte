package ru.somarov.berte.infrastructure.oauth

import com.yandex.authsdk.YandexAuthToken

object TokenStoreExtension {
    fun String.toTokenStore(): TokenStore {
        return TokenStore(this, null, TokenSource.GOOGLE)
    }

    fun YandexAuthToken.toTokenStore(): TokenStore {
        return TokenStore(this.value, this.expiresIn, TokenSource.YANDEX)
    }
}
