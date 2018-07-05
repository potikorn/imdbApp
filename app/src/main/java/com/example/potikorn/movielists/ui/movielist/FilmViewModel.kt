package com.example.potikorn.movielists.ui.movielist

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

    private var page: Int? = null

    fun searchFilmList(isReload: Boolean = false, query: String) {
        if (isReload) {
            page = 1
        }
//        isLoading.value = true
        filmRepository.getFilmList(query, page ?: 1, this)
    }

    fun loadNowPlayingList(isLoadMore: Boolean = false) {
        if (isLoadMore) {
            page = 1
        }
//        isLoading.value = true
        filmRepository.getNowPlayingList(page ?: 1, this)
    }

    override fun onSuccess(body: Film?) {
        page = body?.page?.plus(1)
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