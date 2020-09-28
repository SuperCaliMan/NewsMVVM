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

    private var newsDataSourceFactory = NewsDataSourceFactory(repo,viewModelScope);


    val newsPagedLiveData:LiveData<PagedList<NewsArticle>> = LivePagedListBuilder(newsDataSourceFactory,pagedListConfig()).build()
    
    val errorLiveData: LiveData<String> = _errorMutableData

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


