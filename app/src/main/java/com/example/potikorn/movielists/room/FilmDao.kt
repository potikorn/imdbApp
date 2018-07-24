package com.example.potikorn.movielists.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface FilmDao {

    @Query(RoomContract.SELECT_FILMS_COUNT)
    fun getTotalFilms(): Flowable<Int>

    @Insert
    fun insertAll(filmList: MutableList<FilmEntity>)

    @Query(RoomContract.SELECT_FILMS)
    fun getAllFilms(): Flowable<MutableList<FilmEntity>>


}
