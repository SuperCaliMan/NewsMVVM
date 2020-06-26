package com.example.calinews.config

import com.example.calinews.data.network.NewsApi
import com.example.calinews.data.NewsRepositoryImpl
import com.example.calinews.domain.Repository
import com.example.calinews.domain.getNewsTaskUseCase
import com.example.calinews.presentation.viewmodel.NewsViewModel
import com.supercaliman.network.Network
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> {
        return@single NewsRepositoryImpl()
    }
    factory { getNewsTaskUseCase() }
    viewModel { NewsViewModel() }

    single {
        val network = Network(androidContext(),ConfigurationNetwork())
        network.createServiceAPI(NewsApi::class)
    }
}