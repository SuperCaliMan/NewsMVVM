package com.example.retrofit

import com.supercaliman.network.NetworkConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Alberto Caliman 21/05/2020
 * Singleton class to create apiClient that connect with network
 * [NetworkConfiguration] params to config client
 * REMEMBER: add INTERNET permission in manifest to manage network resource
 */
class RetrofitClientInstance(config: NetworkConfiguration) {

    var logging = HttpLoggingInterceptor()
    var httpClient = OkHttpClient.Builder()

    init {
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        httpClient.addInterceptor(logging)
    }
    val URL:String = config.baseUrl()

    var retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
}