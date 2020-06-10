package com.supercaliman.network

import android.content.Context
import com.example.retrofit.RetrofitClientInstance
import retrofit2.create
import kotlin.reflect.KClass

/**
 * Network entry point
 */
class Network (context: Context,private val config: NetworkConfiguration){


    /**
     * Create api istance
     * using [Network] and [NetworkConfiguration] config
     */
    fun <T:Any> createServiceAPI(apiClass: KClass<T>):T {
        return RetrofitClientInstance(config).retrofit.create(
            apiClass.java
        )
    }
}