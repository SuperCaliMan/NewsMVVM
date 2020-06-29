package com.example.calinews.presentation.viewmodel


import SingleLiveEvent
import android.util.Log
import androidx.lifecycle.*
import com.supercaliman.domain.getNewsTaskUseCase
import com.supercaliman.domain.model.NewsResponse
import com.supercaliman.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


@Suppress("UNCHECKED_CAST")
class NewsViewModel:ViewModel(), KoinComponent{
    private val TAG:String = NewsViewModel::class.java.simpleName
    private var _verificationError = SingleLiveEvent<String>()
    private val getNewsUseCase: getNewsTaskUseCase by inject()
    private var newsLiveData: MutableLiveData<NewsResponse> = liveData(Dispatchers.IO) {
            val getData = getNewsUseCase.execute()
            when(getData){
                    is Result.Success -> emit(getData.response) //eventuale mapper
                    is Result.ConnectionError -> {
                        Log.d(TAG,"connection error")
                        _verificationError.postValue("connection error")
                    }
                    is Result.ServerError -> {
                        Log.d(TAG,"server error")
                        _verificationError.postValue("server error")
                    }
                    is Result.Unauthorized -> {
                        Log.d(TAG, "unauthorized")
                        _verificationError.postValue( "unauthorized")
                    }
                }
            } as MutableLiveData<NewsResponse>



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

    fun update(){
        CoroutineScope(Dispatchers.Main).launch {
            val getData = getNewsUseCase.execute()
            when(getData){
                is Result.Success -> newsLiveData.postValue(getData.response) //eventuale mapper
                is Result.ConnectionError -> {
                    Log.d(TAG,"connection error")
                    _verificationError.postValue("connection error")
                }
                is Result.ServerError -> {
                    Log.d(TAG,"server error")
                    _verificationError.postValue("server error")
                }
                is Result.Unauthorized -> {
                    Log.d(TAG, "unauthorized")
                    _verificationError.postValue( "unauthorized")
                }
            }
        }
    }
}


