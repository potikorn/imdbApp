package com.example.potikorn.movielists.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.movielists.repository.MovieRepository
import com.example.potikorn.movielists.room.favorite.FavoriteEntity
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val liveFavoriteList:LiveData<MutableList<FavoriteEntity>> by lazy { getFavoriteList() }
    val liveFavoriteItem = MutableLiveData<FavoriteEntity>()

    fun getFavoriteList(): LiveData<MutableList<FavoriteEntity>> {
        isLoading.value = true
        return movieRepository.getFavoriteList()
    }

    fun getFavoriteById(movieId: Long): LiveData<FavoriteEntity> {
        return movieRepository.getFavoriteById(movieId)
    }
}