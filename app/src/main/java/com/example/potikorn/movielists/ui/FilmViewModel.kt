package com.example.potikorn.movielists.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.repository.FilmRepository
import com.example.potikorn.movielists.room.Film
import javax.inject.Inject

class FilmViewModel @Inject constructor(private val filmRepository: FilmRepository) :
    ViewModel(), BaseSubscriber.SubscribeCallback<Film> {

    val isLoading = MutableLiveData<Boolean>()
    val liveFilmData = MutableLiveData<Film>()
    val error = MutableLiveData<String>()

    fun loadFilmList(query: String){
        isLoading.value = true
        filmRepository.getFilmList(query, this)
    }

    override fun onSuccess(body: Film?) {
        isLoading.value = false
        liveFilmData.value = body
    }

    override fun onUnSuccess(message: String?) {
        isLoading.value = false
        error.value = message
    }

    override fun onObservableError(message: String?) {
        isLoading.value = false
        error.value = message
    }
}