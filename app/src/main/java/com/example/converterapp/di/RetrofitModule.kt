package com.example.converterapp.di

import com.example.converterapp.data.remote.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): CurrencyApi = retrofit.create(CurrencyApi::class.java)

    @Provides
    @Singleton
    fun provideClient(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.exchangerate.host/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}