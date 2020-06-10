package com.example.calinews.repository

import com.example.calinews.model.NewsResult
import com.example.calinews.network.NewsApi
import com.example.calinews.network.NewsSafeApi
import com.supercaliman.network.api.NetworkError
import com.supercaliman.network.api.NetworkResource
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * @author Alberto Caliman 24/05/2020
 *Repository
 */
class NewsRepository: KoinComponent {
    private val newsApi:NewsApi by inject()


    //get data from newtworkAPI
    suspend fun getNews(source:String,key:String) :NewsResult {
       // return newsApi.getNewsList(source,key)
       val res = NewsSafeApi {
            newsApi.getNewsList(source, key)
        }

        return when(res){
            is NetworkResource.Success -> NewsResult.Success(res.data!!)
            is NetworkResource.Error -> {
                val errorResponse = res.error
                if (errorResponse is NetworkError.HttpError) {
                    if (errorResponse.httpCode == 401) {
                        NewsResult.Unauthorized
                    } else {
                        NewsResult.ServerError
                    }
                } else {
                    NewsResult.ConnectionError
                }
            }
        }
    }
}

