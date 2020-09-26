package com.example.calinews.presentation.viewmodel




import android.content.Context
import android.content.res.Configuration
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supercaliman.data.pagination.NewsDataSourceFactory
import com.supercaliman.domain.Repository
import com.supercaliman.domain.SingleLiveEvent
import com.supercaliman.domain.getNewsTaskUseCase
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result
import kotlinx.coroutines.Dispatchers


/**
 * ViewModel to show a list of news, in landscapeMode list become a grid
 */
@Suppress("UNCHECKED_CAST")
class NewsViewModel @ViewModelInject constructor(
    private var getNewsUseCase: getNewsTaskUseCase,
    private var repo:Repository):ViewModel(){

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


    private var newsDataSourceFactory = NewsDataSourceFactory(repo,viewModelScope);


    val newsPagedLiveData:LiveData<PagedList<NewsArticle>> = LivePagedListBuilder(newsDataSourceFactory,pagedListConfig()).build()


    var errorLiveData: LiveData<String> = _errorMutableData

    fun refresh(){
        newsDataSourceFactory.invalidate()
    }


    fun getLayoutManagerByOrientation(context: Context):RecyclerView.LayoutManager{
        return if(context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            LinearLayoutManager(context)
        }else{
            GridLayoutManager(context,2)
        }
    }

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(5)
        .setEnablePlaceholders(true)
        .setPageSize(5*2)
        .build()
}


