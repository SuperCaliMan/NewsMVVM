package com.supercaliman.domain.model


sealed class NewsResult {
    data class Success(val response: com.supercaliman.domain.model.NewsResponse):
        NewsResult()
    object Unauthorized : NewsResult()
    object ConnectionError : NewsResult()
    object ServerError : NewsResult()
}