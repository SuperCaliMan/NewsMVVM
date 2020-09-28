package com.supercaliman.data


import com.supercaliman.data.cache.CacheDAO
import com.supercaliman.data.cache.CacheMapper
import com.supercaliman.data.network.NewsApi
import com.supercaliman.data.network.NewsSafeApi
import com.supercaliman.domain.BuildConfig.APIKEY
import com.supercaliman.domain.Repository
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result
import com.supercaliman.network.api.NetworkError
import com.supercaliman.network.api.NetworkResource


/**
 * @author Alberto Caliman 24/05/2020
 * Repository implementation
 */
@Suppress("UNCHECKED_CAST")
class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val cache:CacheDAO,
    private val mapper:CacheMapper):Repository {


   override suspend fun getNews(source:String): Result<List<NewsArticle>> {
        val res = NewsSafeApi {
            newsApi.getNewsList(source,APIKEY)
        }

        return when(res){
            is NetworkResource.Success -> {
               cache.clearCache()
               res.data!!.articles!!.map { mapper.map(it) }.map { newsArticleEntity ->  cache.add(newsArticleEntity) }
               Result.Success(cache.getCacheArticles().map { mapper.mapToModel(it) })
            }
            is NetworkResource.Error -> {
                if (cache.containsData()) {
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

    override suspend fun getPagedNews(page: Int, pageSize: Int): Result<List<NewsArticle>> {
       val res = NewsSafeApi {
           newsApi.getPagedList(page,pageSize, APIKEY)
       }

        return when(res){
            is NetworkResource.Success -> {
                Result.Success(res.data!!.articles!!.toMutableList())
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

