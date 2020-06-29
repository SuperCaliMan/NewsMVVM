package com.supercaliman.domain.model

/**
 * Generic class that contains Call status
 */
sealed class Result<out T> {
    data class Success<out T>(val response: T?):Result<T>()
    object Unauthorized : Result<Nothing>()
    object ConnectionError : Result<Nothing>()
    object ServerError : Result<Nothing>()
}