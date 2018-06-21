package com.example.potikorn.movielists.repository

import android.arch.lifecycle.LiveData
import com.example.potikorn.movielists.room.FilmEntity

interface FilmRepository {

    fun getTotalFilms(): Boolean

    fun addFilmList()

    fun getFilmList(query: String): LiveData<MutableList<FilmEntity>>

    fun getLocalFilmList(): LiveData<MutableList<FilmEntity>>
}