package ru.somarov.berte.infrastructure

sealed interface BResult<T> {
    class Success<T>(val data: T) : BResult<T>
    class Error<T>(val error: Throwable) : BResult<T>
    class Loading<T> : BResult<T>
    class Empty<T> : BResult<T>
}

fun <T> Result<T>.asBResult(): BResult<T> {
    onFailure {
        return BResult.Error(it)
    }.onSuccess {
        return BResult.Success(it)
    }
    return BResult.Empty()
}

fun <T> T.asSuccess(): BResult<T> {
    return BResult.Success(this)
}

suspend fun <T> BResult<T>.onSuccess(block: suspend (T) -> Unit): BResult<T> {
    return when (this) {
        is BResult.Success -> {
            block(data)
            this
        }
        else -> this
    }
}

suspend fun <T> BResult<T>.onFailure(block: suspend (Throwable) -> Unit): BResult<T> {
    return when (this) {
        is BResult.Error -> {
            block(error)
            this
        }
        else -> this
    }
}

suspend fun <T> BResult<T>.onLoading(block: suspend () -> Unit): BResult<T> {
    return when (this) {
        is BResult.Loading -> {
            block()
            this
        }

        else -> this
    }
}

suspend fun <T> BResult<T>.onEmpty(block: suspend () -> Unit): BResult<T> {
    return when (this) {
        is BResult.Empty -> {
            block()
            this
        }

        else -> this
    }
}

fun <T> BResult<T>.getOrNull(): T? {
    return if (this is BResult.Success)
        data
    else
        null
}