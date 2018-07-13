package com.example.potikorn.movielists.repository

import com.example.potikorn.movielists.dao.FilmResult

class MovieContract {

    interface FilmDetailListener {
        fun onFilmDetailLoadSuccess(film: FilmResult?)
        fun onUnSuccess(message: String?)
        fun onObservableError(message: String?)
    }
}