package com.supercaliman.data.cache

import androidx.room.*
import io.reactivex.Single

@Dao
interface CacheDAO {
    @Query("SELECT * FROM newsarticleentity")
    fun getCacheArticles():List<NewsArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun add(articleDao: NewsArticleEntity)

    @Delete
    fun delete(articleDao: NewsArticleEntity)

    @Query("SELECT COUNT(*) FROM newsarticleentity")
    fun isEmpty():Int
}