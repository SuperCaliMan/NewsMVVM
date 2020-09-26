package com.supercaliman.data.network

import com.supercaliman.data.BuildConfig
import com.supercaliman.domain.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi{

    @GET("top-headlines/")
    suspend fun getNewsList(@Query("sources") newsSource:String, @Query("apiKey") apiKey:String):Response<NewsResponse>

    @GET("everything?q=news")
    suspend fun getPagedList(@Query("page") page: Int,@Query("pageSize") pageSize: Int,@Query("apiKey") apiKey: String):Response<NewsResponse>

}