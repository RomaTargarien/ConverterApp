package com.example.converterapp.di

import android.content.Context
import androidx.room.Room
import com.example.converterapp.data.local.db.CurrencyDao
import com.example.converterapp.data.local.db.CurrencyDatabase
import com.example.converterapp.data.local.shared.IUserPreferences
import com.example.converterapp.data.local.shared.UserPreferences
import com.example.converterapp.data.remote.CurrencyApi
import com.example.converterapp.repositories.local.ILocalCurrencyRepository
import com.example.converterapp.repositories.local.LocalCurrencyRepository
import com.example.converterapp.repositories.remote.CurrencyRepository
import com.example.converterapp.repositories.remote.ICurrencyRepository
import com.example.converterapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun provideRemoteCurrencyRepository(api: CurrencyApi): ICurrencyRepository = CurrencyRepository(api)

    @Singleton
    @Provides
    fun provideLocalCurrencyRepository(dao: CurrencyDao): ILocalCurrencyRepository = LocalCurrencyRepository(dao)

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): IUserPreferences = UserPreferences(context)

    @Singleton
    @Provides
    fun provideCurrencyDatabase(@ApplicationContext context: Context): CurrencyDatabase =
        Room.databaseBuilder(context, CurrencyDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCurrencyDao(db: CurrencyDatabase): CurrencyDao = db.getCurrencyDao()


}