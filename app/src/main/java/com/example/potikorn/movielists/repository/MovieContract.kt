package com.example.potikorn.movielists.repository

import com.example.potikorn.movielists.room.FilmEntity

class MovieContract {

    interface FilmDetailListener {
        fun onFilmDetailLoadSuccess(film: FilmEntity?)
        fun onUnSuccess(message: String?)
        fun onObservableError(message: String?)
    }
}