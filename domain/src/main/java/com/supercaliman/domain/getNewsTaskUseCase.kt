package com.supercaliman.domain

import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.NewsResponse
import com.supercaliman.domain.model.Result
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Rappresenta le operationi che posso eseguire sul mio oggetto newsResult, va fatta una classe per ogni operazione CRUD
 */
class getNewsTaskUseCase(): KoinComponent {
    val newsRepository: Repository by inject()


   suspend fun execute(): Result<List<NewsArticle>> {
        val news = newsRepository.getNews("google-news","ba2cd6f3c03c40879416611969d88a9f")
        return news
    }
}