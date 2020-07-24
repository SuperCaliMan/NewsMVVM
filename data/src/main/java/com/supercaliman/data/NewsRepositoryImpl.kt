package com.supercaliman.data


import com.supercaliman.data.cache.CacheDAO
import com.supercaliman.data.cache.CacheMapper
import com.supercaliman.data.network.NewsApi
import com.supercaliman.data.network.NewsSafeApi
import com.supercaliman.domain.Repository
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result
import com.supercaliman.network.api.NetworkError
import com.supercaliman.network.api.NetworkResource


/**
 * @author Alberto Caliman 24/05/2020
 * Repository implementation
 */
class NewsRepositoryImpl(val newsApi: NewsApi, val cache:CacheDAO, val mapper:CacheMapper):Repository {

   override suspend fun getNews(source:String, key:String): Result<List<NewsArticle>> {
        val res = NewsSafeApi {
            newsApi.getNewsList(source,key)
        }

        return when(res){
            is NetworkResource.Success -> {
               res.data!!.articles!!.map { mapper.map(it) }.map { newsArticleEntity ->  cache.add(newsArticleEntity) }
               Result.Success(cache.getCacheArticles().map { mapper.mapToModel(it) })
            }
            is NetworkResource.Error -> {
                if (cache.isEmpty()) {
                    Result.Success(cache.getCacheArticles().map { mapper.mapToModel(it) })
                } else {
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
}

