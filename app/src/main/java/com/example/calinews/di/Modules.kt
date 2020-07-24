package com.example.calinews.di


import com.example.calinews.presentation.viewmodel.NewsViewModel
import com.supercaliman.domain.getNewsTaskUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { getNewsTaskUseCase(get()) }
    viewModel { NewsViewModel(get()) }
}