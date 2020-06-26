package com.example.calinews.domain

import com.example.calinews.domain.model.NewsResult

interface Repository {

    suspend fun getNews(source:String,key:String) : NewsResult
}