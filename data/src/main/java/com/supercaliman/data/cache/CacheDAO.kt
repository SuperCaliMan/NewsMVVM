package com.supercaliman.data.cache

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.supercaliman.data.pagination.NewsDataSource

@Dao
interface CacheDAO {
    @Query("SELECT * FROM newsarticleentity")
    fun getCacheArticles():List<NewsArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addList(articles:List<NewsArticleEntity>)

    @Query("SELECT * FROM newsarticleentity")
    fun getPagingArticles():DataSource.Factory<Int,NewsArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun add(articleDao: NewsArticleEntity)

    @Delete
    fun delete(articleDao: NewsArticleEntity)

    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM newsarticleentity) THEN 1 ELSE 0 END")
    fun containsData():Boolean

    @Query("DELETE FROM newsarticleentity")
    fun clearCache()
}