package com.example.potikorn.movielists.di

import android.content.Context
import com.example.potikorn.movielists.room.FilmDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomCurrencyDataSource(context: Context) = FilmDatabase.buildPersistentFilm(context)
}