package com.example.calinews.presentation.viewmodel


import SingleLiveEvent
import android.util.Log
import androidx.lifecycle.*
import com.example.calinews.domain.model.NewsResponse
import com.example.calinews.domain.model.NewsResult
import com.example.calinews.domain.getNewsTaskUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject


class NewsViewModel:ViewModel(), KoinComponent{
    private val TAG:String = NewsViewModel::class.java.simpleName
    private var _verificationError = SingleLiveEvent<String>()
    private val getNewsUseCase:getNewsTaskUseCase by inject()
    private var newsLiveData: LiveData<NewsResponse> = liveData(Dispatchers.IO) {
            val getData = getNewsUseCase.execute()
            when(getData){
                    is NewsResult.Success -> emit(getData.response) //eventuale mapper
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


    fun getNewsUseCase(): LiveData<NewsResponse> {
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


