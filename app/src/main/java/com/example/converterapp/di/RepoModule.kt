package com.example.converterapp.di

import com.example.converterapp.data.remote.CurrencyApi
import com.example.converterapp.repositories.remote.CurrencyRepository
import com.example.converterapp.repositories.remote.ICurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun provideRemoteCurrencyRepository(api: CurrencyApi): ICurrencyRepository = CurrencyRepository(api)

}