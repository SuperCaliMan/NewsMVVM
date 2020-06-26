package com.example.calinews.domain

import com.example.calinews.data.NewsRepositoryImpl
import com.example.calinews.domain.model.NewsResult
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Rappresenta le operationi che posso eseguire sul mio oggetto newsResult, va fatta una classe per ogni operazione CRUD
 */
class getNewsTaskUseCase(): KoinComponent {
    val newsRepository: Repository by inject()


   suspend fun execute(): NewsResult {
        val news = newsRepository.getNews("google-news","ba2cd6f3c03c40879416611969d88a9f")
        return news
    }
}