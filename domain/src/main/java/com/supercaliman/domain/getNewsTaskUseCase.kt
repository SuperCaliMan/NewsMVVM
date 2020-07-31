package com.supercaliman.domain

import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result


/**
 * @author Alberto Caliman 31/07/2020
 */
class getNewsTaskUseCase(val newsRepository: Repository){



   suspend fun execute(): Result<List<NewsArticle>> {
        val news = newsRepository.getNews("google-news",BuildConfig.APIKEY)
        return news
    }
}