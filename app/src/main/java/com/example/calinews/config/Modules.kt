package com.example.calinews.config

import com.example.calinews.network.NewsApi
import com.example.calinews.repository.NewsRepository
import com.example.calinews.viewmodel.NewsViewModel
import com.supercaliman.network.Network
import com.supercaliman.network.NetworkConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { NewsRepository() }
    viewModel { NewsViewModel() }

    single {
        val network = Network(androidContext(),ConfigurationNetwork())
        network.createServiceAPI(NewsApi::class)
    }
}