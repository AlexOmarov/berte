package ru.somarov.berte.infrastructure.oauth

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import ru.somarov.berte.infrastructure.network.CommonResult

internal suspend fun openYandexAuthForResult(
    context: Any,
    state: OAuthState
): CommonResult<TokenStore> {
    startYandexAuth(context, state)
    return state.tokenFlow
        .filter {
            it is CommonResult.Success || it is CommonResult.Error
        }
        .first()
}
