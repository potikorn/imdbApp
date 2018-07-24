package com.example.potikorn.movielists.ui.movielist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.potikorn.movielists.repository.MovieRepository
import javax.inject.Inject

class MovieViewModelFactory @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(movieRepository) as T
        }
        throw IllegalAccessException("Unknown View Model class")
    }
}