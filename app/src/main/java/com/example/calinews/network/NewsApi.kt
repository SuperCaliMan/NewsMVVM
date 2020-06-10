package com.example.calinews.network

import com.example.calinews.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi{

    @GET("top-headlines")
   suspend fun getNewsList(@Query("sources") newsSource:String, @Query("apiKey") apiKey:String):Response<NewsResponse>


}