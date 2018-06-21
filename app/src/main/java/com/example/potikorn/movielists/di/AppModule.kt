package com.example.potikorn.movielists.di

import android.content.Context
import com.example.potikorn.movielists.ImdbApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val currencyApplication: ImdbApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = currencyApplication

}
