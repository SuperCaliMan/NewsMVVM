package com.example.calinews.model


sealed class NewsResult {
    data class Success(val response:NewsResponse):NewsResult()
    object Unauthorized : NewsResult()
    object ConnectionError : NewsResult()
    object ServerError : NewsResult()
}