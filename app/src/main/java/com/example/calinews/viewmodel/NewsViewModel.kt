package com.example.calinews.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.example.calinews.model.NewsResponse
import com.example.calinews.model.NewsResult
import com.example.calinews.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject


class NewsViewModel:ViewModel(), KoinComponent{
    private val TAG:String = NewsViewModel::class.java.simpleName
    val newsRepository:NewsRepository by inject()
    private var _verificationError = MutableLiveData<String>()

    private var newsLiveData: LiveData<NewsResponse> = liveData(Dispatchers.IO) {
            val getData = newsRepository.getNews("google-news","ba2cd6f3c03c40879416611969d88a9f")
            when(getData){
                    is NewsResult.Success -> emit(getData.response)
                    is NewsResult.ConnectionError -> {
                        Log.d(TAG,"connection error")
                        _verificationError.postValue("connection error")
                    }
                    is NewsResult.ServerError -> {
                        Log.d(TAG,"server error")
                        _verificationError.postValue("server error")
                    }
                    is NewsResult.Unauthorized -> {
                        Log.d(TAG, "unauthorized")
                        _verificationError.postValue( "unauthorized")
                    }
                }
            }


    var verificationError: LiveData<String> = _verificationError


    fun getNewsRepository(): LiveData<NewsResponse> {
        return newsLiveData
        //Data manipulation example
        /*Mapper class from vievModel to presentation layer
        return Transformations.map(newsLiveData) { news ->
            if (news.articles != null && Build.VERSION.SDK_INT > 23) {
                NewsResponse(news.status, news.totalResults, news.articles.stream()
                    .map { news ->
                        NewsArticle(
                            news.source, "",
                            "hello",
                            "description",
                            news.url,
                            news.urlToImage,
                            news.publishedAt,
                            news.content
                        )
                    }
                    .collect(Collectors.toList()))
            } else {
                news
            }
        }
         */

        }
}


