package ru.developmentmobile.tracker.map.network

import ru.developmentmobile.tracker.network.ErrorModel

sealed class Response<out T> {
    companion object {
        fun <T> success(value: T) = Success(value)

        fun <T> failure(error: Throwable) = Failure<T>(error)
    }
}

data class Success<out T>(val data: T) : Response<T>()
data class Failure<out T>(val error: Throwable) : Response<T>()

class ResponseError(type: String, details: String) : Throwable()

fun ErrorModel.mapToResponseError() = ResponseError(
    this.type,
    this.details.toString()
)

