package com.supercaliman.data


import com.supercaliman.data.network.NewsApi
import com.supercaliman.data.network.NewsSafeApi
import com.supercaliman.domain.Repository
import com.supercaliman.domain.model.NewsResult
import com.supercaliman.network.api.NetworkError
import com.supercaliman.network.api.NetworkResource
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * @author Alberto Caliman 24/05/2020
 *Repository
 */
class NewsRepositoryImpl(): KoinComponent, Repository {
    private val newsApi:NewsApi by inject()

    //get data from newtworkAPI
   override suspend fun getNews(source:String,key:String) : NewsResult {
        val res = NewsSafeApi {
            newsApi.getNewsList(source,key)
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

