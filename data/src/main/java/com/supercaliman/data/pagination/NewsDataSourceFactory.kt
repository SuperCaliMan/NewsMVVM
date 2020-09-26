package com.supercaliman.data.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.supercaliman.data.NewsRepositoryImpl
import com.supercaliman.domain.Repository
import com.supercaliman.domain.model.NewsArticle
import kotlinx.coroutines.CoroutineScope

/**
 * is responsible for creating a DataSource
 */
class NewsDataSourceFactory(private val repository: Repository,
                            private val scope: CoroutineScope
): DataSource.Factory<Int, NewsArticle>() {

    private lateinit var newsDataSource:NewsDataSource
    val source = MutableLiveData<NewsDataSource>()


    override fun create(): DataSource<Int, NewsArticle> {
        newsDataSource = NewsDataSource(scope, repository)
        this.source.postValue(newsDataSource)
        return newsDataSource
    }

    fun invalidate(){
        newsDataSource.invalidate()
    }
}