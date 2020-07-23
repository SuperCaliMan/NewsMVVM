package com.supercaliman.data

import androidx.room.Room
import com.supercaliman.data.cache.CacheDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    factory { CacheMapper() }
}