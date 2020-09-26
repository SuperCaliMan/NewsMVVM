package com.supercaliman.domain

import androidx.lifecycle.LiveData
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result

interface Repository {

    suspend fun getNews(source:String): Result<List<NewsArticle>>

    suspend fun getPagedNews(page:Int, pageSize:Int): Result<List<NewsArticle>>
}