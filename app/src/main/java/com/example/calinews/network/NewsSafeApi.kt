package com.example.calinews.network

import com.supercaliman.network.api.SafeApiCall
import retrofit2.Response

suspend inline fun <T> NewsSafeApi(call: () -> Response<T>) =
    SafeApiCall<T, ErrorResponse>(call)