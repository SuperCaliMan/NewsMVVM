package com.example.calinews.di


import com.supercaliman.data.network.NewsApi
import com.example.calinews.presentation.viewmodel.NewsViewModel
import com.supercaliman.data.ConfigurationNetwork
import com.supercaliman.data.NewsRepositoryImpl
import com.supercaliman.domain.Repository
import com.supercaliman.domain.getNewsTaskUseCase
import com.supercaliman.network.Network
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { getNewsTaskUseCase(get()) }
    viewModel { NewsViewModel(get()) }
}