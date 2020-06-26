package com.example.calinews.config


import com.supercaliman.data.network.NewsApi
import com.example.calinews.presentation.viewmodel.NewsViewModel
import com.supercaliman.data.ConfigurationNetwork
import com.supercaliman.network.Network
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<com.supercaliman.domain.Repository> {
        return@single com.supercaliman.data.NewsRepositoryImpl()
    }
    factory { com.supercaliman.domain.getNewsTaskUseCase() }
    viewModel { NewsViewModel() }

    single {
        val network = Network(
            androidContext(),
            ConfigurationNetwork()
        )
        network.createServiceAPI(NewsApi::class)
    }
}