package com.example.calinews.data

import com.example.calinews.domain.model.NewsResult
import com.example.calinews.data.network.NewsApi
import com.example.calinews.data.network.NewsSafeApi
import com.example.calinews.domain.Repository
import com.supercaliman.network.api.NetworkError
import com.supercaliman.network.api.NetworkResource
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * @author Alberto Caliman 24/05/2020
 *Repository
 */
class NewsRepositoryImpl(): KoinComponent,Repository {
    private val newsApi:NewsApi by inject()



    //get data from newtworkAPI
   override suspend fun getNews(source:String,key:String) :NewsResult {
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

