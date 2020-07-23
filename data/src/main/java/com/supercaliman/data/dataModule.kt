package com.supercaliman.data

import com.supercaliman.data.cache.CacheDAO
import com.supercaliman.data.cache.CacheDatabase
import com.supercaliman.data.cache.CacheMapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    factory { CacheMapper() }
    single<CacheDAO> {
        val db = CacheDatabase.getDatabase(androidContext())!!.cacheDao()
        return@single db
    }
}