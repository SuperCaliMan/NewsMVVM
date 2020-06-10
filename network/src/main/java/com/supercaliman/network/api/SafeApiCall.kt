package com.supercaliman.network.api

import android.util.Log
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * This method wrap a Retrofit [Response] and handles all types of errors.
 * Without this wrapper you should use try/catch every api call to avoid unexpected crashes (no internet connection, timeout, ecc...)
 * @param call a suspend Retrofit call that return a [Response]
 * @return a [NetworkResource] response
 */
suspend inline fun <T,reified E> SafeApiCall(call: () -> Response<T>) : NetworkResource<T, E> {
    val result = runCatching(call)
    return if (result.isSuccess) {
        val response = result.getOrNull()!!
        if (response.isSuccessful) {
            NetworkResource.Success(
                response,
                response.body()
            )
        } else {
            NetworkResource.Error<T, E>(
                response,
                NetworkError.HttpError(
                    response.code(),
                    null
                )
            )
        }
    } else {
        val exception = result.exceptionOrNull()
        exception?.printStackTrace()
        return when (exception) {
            is SocketTimeoutException -> NetworkResource.Error(
                null,
                NetworkError.Timeout()
            )
            is IOException -> NetworkResource.Error(
                null,
                NetworkError.IOError()
            )
            else -> NetworkResource.Error(
                null,
                NetworkError.Unknown()
            )
        }
    }
}