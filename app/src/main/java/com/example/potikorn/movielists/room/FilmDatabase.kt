package com.example.potikorn.movielists.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [FilmEntity::class], version = 1, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao

    companion object {
        fun buildPersistentFilm(context: Context): FilmDatabase = Room.databaseBuilder(
            context.applicationContext,
            FilmDatabase::class.java,
            RoomContract.DATABASE_CURRENCY
        ).build()
    }
}