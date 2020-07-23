package com.supercaliman.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CacheDAO {
    @Query("SELECT * FROM newsresponseentity")
    fun getAll():NewsResponseEntity

    @Insert
    fun addAll(responseDao: NewsResponseEntity)

    @Delete
    fun delete(responseDao: NewsResponseEntity)
}