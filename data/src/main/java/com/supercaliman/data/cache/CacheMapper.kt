package com.supercaliman.data.cache

import com.supercaliman.data.cache.NewsArticleEntity
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.NewsResponse
import com.supercaliman.domain.model.NewsSource
import javax.inject.Inject

class CacheMapper @Inject constructor(){

    fun map(data:NewsArticle):NewsArticleEntity{
        return NewsArticleEntity(data.author,data.title,data.description,data.url,data.urlToImage,data.publishedAt,data.content)
    }


    fun mapToModel(data:NewsArticleEntity):NewsArticle{
        return  NewsArticle(
            NewsSource("",""),
            data.author ?: "",
            data.title,
            data.description,
            data.url,
            data.urlToImage,
            data.publishedAt,
            data.content
        )

    }


}