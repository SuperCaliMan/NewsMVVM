package com.supercaliman.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.supercaliman.domain.model.NewsArticle


import com.google.gson.annotations.SerializedName
import com.supercaliman.domain.model.NewsSource

@Entity
data class NewsResponseEntity(
    @PrimaryKey(autoGenerate = true) var id:Int? = null,
    @ColumnInfo(name = "source") val source: NewsSource,
    @ColumnInfo(name ="author") val author:String,
    @ColumnInfo(name ="title") val title:String,
    @ColumnInfo(name ="description")val description:String,
    @ColumnInfo(name ="url") val url:String,
    @ColumnInfo(name ="urlToImage") val urlToImage:String,
    @ColumnInfo(name ="publishedAt") val publishedAt:String,
    @ColumnInfo(name ="content")val content:String
)