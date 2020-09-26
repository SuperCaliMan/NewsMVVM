package com.supercaliman.data.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.supercaliman.data.cache.CacheDAO
import com.supercaliman.data.cache.CacheMapper
import com.supercaliman.data.cache.NewsArticleEntity
import com.supercaliman.data.network.NewsApi
import com.supercaliman.data.network.NewsSafeApi
import com.supercaliman.domain.BuildConfig
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result
import com.supercaliman.network.api.NetworkResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CacheBoundaryCallback(
    private val api:NewsApi,
    private val cache:CacheDAO,
    private val scope:CoroutineScope,
    private val mapper:CacheMapper
):PagedList.BoundaryCallback<NewsArticleEntity>(){
    /*

    private var lastRequestPage = 1;

    private val _networkError = MutableLiveData<String>()

    val networkError:LiveData<String>
        get() = _networkError

    private fun requestAndSaveData(){
        scope.launch(Dispatchers.IO) {
            val res = NewsSafeApi { api.getPagedList(lastRequestPage, NETWORK_PAGE_SIZE,BuildConfig.APIKEY) }
            when(res){
                is NetworkResource.Success -> cache.addList(res.data!!.articles!!.map { mapper.map(it) })
                is NetworkResource.Error -> _networkError.postValue(res.error.toString())
            }
        }

    }

     */


    override fun onZeroItemsLoaded() {
      TODO()
    }



    override fun onItemAtEndLoaded(itemAtEnd: NewsArticleEntity) {
        TODO()
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}