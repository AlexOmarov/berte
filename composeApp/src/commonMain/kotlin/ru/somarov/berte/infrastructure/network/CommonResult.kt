package ru.somarov.berte.infrastructure.network

sealed interface CommonResult<T> {
    class Success<T>(val data: T) : CommonResult<T>
    class Error<T>(val error: Throwable) : CommonResult<T>
    class Loading<T> : CommonResult<T>
    class Empty<T> : CommonResult<T>

    fun <T> Result<T>.asCommonResult(): CommonResult<T> {
        this.onFailure { return Error(it) }
            .onSuccess { return Success(it) }
        return Empty()
    }

    fun <T> CommonResult<T>.getOrNull(): T? {
        return if (this is Success) data else null
    }

    fun <T> CommonResult<T>.isSuccess(): Boolean = this is Success<T>
}
