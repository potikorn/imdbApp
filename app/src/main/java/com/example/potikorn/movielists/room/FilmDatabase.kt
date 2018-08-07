package com.example.potikorn.movielists.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.potikorn.movielists.room.favorite.FavoriteEntity
import com.example.potikorn.movielists.room.favorite.FavoriteLocalDao

@Database(
    entities = [FilmEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FilmDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao
    abstract fun favoriteDao(): FavoriteLocalDao

    companion object {
        fun buildPersistentFilm(context: Context): FilmDatabase = Room.databaseBuilder(
            context.applicationContext,
            FilmDatabase::class.java,
            RoomContract.DATABASE_APP
        ).build()
    }
}