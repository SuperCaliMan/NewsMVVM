package com.supercaliman.data



import android.content.Context
import com.supercaliman.data.cache.CacheDAO
import com.supercaliman.data.cache.CacheDatabase
import com.supercaliman.data.cache.CacheMapper
import com.supercaliman.data.network.NewsApi
import com.supercaliman.domain.Repository
import com.supercaliman.domain.getNewsTaskUseCase
import com.supercaliman.network.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object dataModule{


    @Provides
    @Singleton
    fun getCache(@ApplicationContext context: Context):CacheDAO{
        val db = CacheDatabase.getDatabase(context)!!.cacheDao()
        return db
    }

    @Provides
    @Singleton
    fun getRepository(newsApi: NewsApi,cacheDAO: CacheDAO,mapper: CacheMapper):Repository{
        return NewsRepositoryImpl(newsApi,cacheDAO,mapper)
    }

    @Provides
    @Singleton
    fun getNetworkApi():NewsApi{
        val network = Network(
            ConfigurationNetwork()
        )
        return network.createServiceAPI(NewsApi::class)
    }

    @Provides
    fun getUseCase(repo:Repository):getNewsTaskUseCase{
        return getNewsTaskUseCase(repo)
    }


}