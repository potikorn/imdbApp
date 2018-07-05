package com.example.potikorn.movielists.ui.movielist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.potikorn.movielists.repository.FilmRepository
import javax.inject.Inject

class FilmViewModelFactory @Inject constructor(private val filmRepository: FilmRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmViewModel::class.java)) {
            return FilmViewModel(filmRepository) as T
        }
        throw IllegalAccessException("Unknown View Model class")
    }
}