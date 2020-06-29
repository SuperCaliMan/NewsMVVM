package com.supercaliman.data


import com.supercaliman.data.network.NewsApi
import com.supercaliman.data.network.NewsSafeApi
import com.supercaliman.domain.Repository
import com.supercaliman.domain.model.NewsResponse
import com.supercaliman.domain.model.Result
import com.supercaliman.network.api.NetworkError
import com.supercaliman.network.api.NetworkResource
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * @author Alberto Caliman 24/05/2020
 * Repository implementation
 */
class NewsRepositoryImpl(): KoinComponent, Repository {
    private val newsApi:NewsApi by inject()

    //get data from newtworkAPI
   override suspend fun getNews(source:String,key:String) : Result<NewsResponse> {
        val res = NewsSafeApi {
            newsApi.getNewsList(source,key)
        }

        return when(res){
            is NetworkResource.Success -> Result.Success(res.data!!)
            is NetworkResource.Error -> {
                val errorResponse = res.error
                if (errorResponse is NetworkError.HttpError) {
                    if (errorResponse.httpCode == 401) {
                        Result.Unauthorized
                    } else {
                        Result.ServerError
                    }
                } else {
                    Result.ConnectionError
                }
            }
        }

    }
}

