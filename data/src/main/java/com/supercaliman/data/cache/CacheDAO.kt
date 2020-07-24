package com.supercaliman.data.cache

import androidx.room.*

@Dao
interface CacheDAO {
    @Query("SELECT * FROM newsarticleentity")
    fun getCacheArticles():List<NewsArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun add(articleDao: NewsArticleEntity)

    @Delete
    fun delete(articleDao: NewsArticleEntity)

    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM newsarticleentity) THEN 1 ELSE 0 END")
    fun isEmpty():Boolean
}