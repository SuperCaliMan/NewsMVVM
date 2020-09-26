package com.supercaliman.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsArticleEntity(
    @ColumnInfo(name ="author") val author:String?,
    @ColumnInfo(name ="title") val title:String,
    @ColumnInfo(name ="description")val description:String,
    @PrimaryKey val url:String,
    @ColumnInfo(name ="urlToImage") val urlToImage:String?,
    @ColumnInfo(name ="publishedAt") val publishedAt:String,
    @ColumnInfo(name ="content")val content:String?
)