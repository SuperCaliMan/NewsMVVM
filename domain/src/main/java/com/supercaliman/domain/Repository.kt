package com.supercaliman.domain

import com.supercaliman.domain.model.NewsResult

interface Repository {

    suspend fun getNews(source:String,key:String) : NewsResult
}