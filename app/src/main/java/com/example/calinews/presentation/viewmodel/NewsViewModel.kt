package com.example.calinews.presentation.viewmodel




import android.content.Context
import android.content.res.Configuration
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supercaliman.domain.SingleLiveEvent
import com.supercaliman.domain.getNewsTaskUseCase
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * ViewModel to show a list of news, in landscapeMode list become a grid
 */
@Suppress("UNCHECKED_CAST")
class NewsViewModel @ViewModelInject constructor(var getNewsUseCase: getNewsTaskUseCase):ViewModel(){
    private var _errorMutableData = SingleLiveEvent<String>()
    private var newsLiveData: MutableLiveData<List<NewsArticle>> = liveData(Dispatchers.IO) {
            val getData = getNewsUseCase.execute()
            when(getData){
                    is Result.Success -> emit(getData.response) //eventuale mapper
                    is Result.ConnectionError -> {
                        _errorMutableData.postValue("connection error")
                    }
                    is Result.ServerError -> {
                        _errorMutableData.postValue("server error")
                    }
                    is Result.Unauthorized -> {
                        _errorMutableData.postValue( "unauthorized")
                    }
                }
            } as MutableLiveData<List<NewsArticle>>



    var errorLiveData: LiveData<String> = _errorMutableData


    fun getUiData(): LiveData<List<NewsArticle>> {
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
       viewModelScope.launch(Dispatchers.IO) {
            val getData = getNewsUseCase.execute()
            when(getData){
                is Result.Success -> newsLiveData.postValue(getData.response)
                is Result.ConnectionError -> {
                    _errorMutableData.postValue("connection error")
                }
                is Result.ServerError -> {
                    _errorMutableData.postValue("server error")
                }
                is Result.Unauthorized -> {
                    _errorMutableData.postValue( "unauthorized")
                }
            }
        }
    }

    fun getLayoutManagerByOrientation(context: Context):RecyclerView.LayoutManager{
        return if(context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            LinearLayoutManager(context)
        }else{
            GridLayoutManager(context,2)
        }
    }
}


