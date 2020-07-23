package com.supercaliman.data


import android.content.Context
import com.supercaliman.data.cache.CacheDatabase
import com.supercaliman.data.network.NewsApi
import com.supercaliman.data.network.NewsSafeApi
import com.supercaliman.domain.Repository
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result
import com.supercaliman.network.api.NetworkError
import com.supercaliman.network.api.NetworkResource
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * @author Alberto Caliman 24/05/2020
 * Repository implementation
 */
class NewsRepositoryImpl(context: Context): KoinComponent, Repository {
    private val newsApi:NewsApi by inject()

    //val db = CacheDatabase.getDatabase(context)!!.cacheDao()
   override suspend fun getNews(source:String, key:String): Result<List<NewsArticle>> {
        val res = NewsSafeApi {
            newsApi.getNewsList(source,key)
        }

        return when(res){
            is NetworkResource.Success -> {
                //db.addAll(mapper.map(res.data!!))
               // Result.Success(mapper.mapTo(db.getAll()))
                Result.Success(res.data!!.articles)
            }
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

