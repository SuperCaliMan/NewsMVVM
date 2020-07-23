package com.supercaliman.data

import com.supercaliman.data.cache.NewsResponseEntity
import com.supercaliman.domain.model.NewsResponse

class CacheMapper{

    fun map(response: NewsResponse):NewsResponseEntity{
        return NewsResponseEntity(0,response.status,response.totalResults)
    }

    fun mapTo(dao:NewsResponseEntity):NewsResponse{
        return  NewsResponse(dao.status,dao.totalResults)
    }
}