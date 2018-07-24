package com.example.potikorn.movielists.room

class RoomContract {

    companion object {

        const val DATABASE_CURRENCY = "film.db"

        const val TABLE_FILMS = "film"

        private const val SELECT_COUNT = "SELECT COUNT(*) FROM "
        private const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_FILMS_COUNT = SELECT_COUNT + TABLE_FILMS
        const val SELECT_FILMS = SELECT_FROM + TABLE_FILMS

    }
}