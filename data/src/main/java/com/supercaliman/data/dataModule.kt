package com.supercaliman.data

import com.supercaliman.data.cache.CacheDAO
import com.supercaliman.data.cache.CacheDatabase
import com.supercaliman.data.cache.CacheMapper
import com.supercaliman.data.network.NewsApi
import com.supercaliman.domain.Repository
import com.supercaliman.network.Network
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    factory { CacheMapper() }

    single<CacheDAO> {
        val db = CacheDatabase.getDatabase(androidContext())!!.cacheDao()
        return@single db
    }

    single<Repository> {
        return@single NewsRepositoryImpl(get(),get(),get())
    }

    single<NewsApi> {
        val network = Network(
            ConfigurationNetwork()
        )
        network.createServiceAPI(NewsApi::class)
    }
}