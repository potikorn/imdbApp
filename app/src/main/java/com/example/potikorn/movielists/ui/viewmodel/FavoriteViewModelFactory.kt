package com.example.potikorn.movielists.ui.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.potikorn.movielists.repository.MovieRepository
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(movieRepository) as T
        }
        throw IllegalAccessException("Unknown View Model class")
    }
}