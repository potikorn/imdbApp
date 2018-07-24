package com.example.potikorn.movielists.di

import android.content.Context
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.data.UserData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val tmdbApplication: ImdbApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = tmdbApplication

    @Provides
    @Singleton
    fun provideUserSharedPreferences(): User =
        UserData(tmdbApplication.getSharedPreferences("user_data", Context.MODE_PRIVATE))
}
