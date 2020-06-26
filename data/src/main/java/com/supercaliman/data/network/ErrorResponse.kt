package com.supercaliman.data.network

import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    @SerializedName( "error") val error: Boolean = false,
    @SerializedName("message") val message: String?,
    @SerializedName("error_code") val errorCode: Int?
) {
    var httpCode: Int? = null
}