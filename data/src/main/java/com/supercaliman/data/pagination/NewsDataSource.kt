package com.supercaliman.data.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.supercaliman.domain.Repository
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/***
 * Represent a page of data
 */
class NewsDataSource(
   private val scope:CoroutineScope,
   private val repo: Repository
): PageKeyedDataSource<Int, NewsArticle>() {

   private val _showLoading = MutableLiveData<Boolean>()
   val showLoading: LiveData<Boolean>
      get() = _showLoading

   override fun loadInitial(
      params: LoadInitialParams<Int>,
      callback: LoadInitialCallback<Int, NewsArticle>
   ) {
      _showLoading.postValue(true)
      scope.launch(Dispatchers.IO) {
         val res = repo.getPagedNews(1, params.requestedLoadSize)
         when (res) {
            is Result.Success -> {
               callback.onResult(res.response!!, null, 1)
               _showLoading.postValue(false)
            }
         }
      }

   }

   override fun loadBefore(
      params: LoadParams<Int>,
      callback: LoadCallback<Int, NewsArticle>
   ) {
      _showLoading.postValue(true)
      val page = params.key - 1
      scope.launch(Dispatchers.IO) {
         val res = repo.getPagedNews(page, params.requestedLoadSize)
         when (res) {
            is Result.Success -> {
               callback.onResult(res.response!!, page)
               _showLoading.postValue(false)
            }
         }
      }
   }

   override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsArticle>) {
      _showLoading.postValue(true)
      val page = params.key + 1
      scope.launch(Dispatchers.IO) {
         val res = repo.getPagedNews(page, params.requestedLoadSize)
         when (res) {
            is Result.Success -> {
               callback.onResult(res.response!!, page)
               _showLoading.postValue(false)
            }
         }
      }
   }
}