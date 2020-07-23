package com.supercaliman.domain

import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result

interface Repository {

    suspend fun getNews(
        source:String,
        key:String): Result<List<NewsArticle>>
}