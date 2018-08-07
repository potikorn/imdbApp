package com.example.potikorn.movielists.di

import android.content.Context
import com.example.potikorn.movielists.room.FilmDatabase
import com.example.potikorn.movielists.room.favorite.FavoriteLocalDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomCurrencyDataSource(context: Context) = FilmDatabase.buildPersistentFilm(context)

    @Singleton
    @Provides
    fun provideWalletDao(db: FilmDatabase): FavoriteLocalDao = db.favoriteDao()
}