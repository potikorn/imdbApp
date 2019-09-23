package com.example.potikorn.movielists.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.movielists.dao.FavoriteDao
import com.example.potikorn.movielists.repository.MovieRepository
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val liveFavoriteList = MutableLiveData<FavoriteDao>()

    fun getFavoriteList() {
        isLoading.value = true
        movieRepository.getFavoriteList({
            isLoading.value = false
            liveFavoriteList.value = it
        }, {
            isLoading.value = false
            error.value = it
        })
    }
}