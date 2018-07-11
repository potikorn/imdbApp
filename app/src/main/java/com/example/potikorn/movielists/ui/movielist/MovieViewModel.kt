package com.example.potikorn.movielists.ui.movielist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.repository.MovieContract
import com.example.potikorn.movielists.repository.MovieRepository
import com.example.potikorn.movielists.room.Film
import com.example.potikorn.movielists.room.FilmEntity
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel(),
    BaseSubscriber.SubscribeCallback<Film>,
    MovieContract.FilmDetailListener {

    val isLoading = MutableLiveData<Boolean>()
    val liveFilmListData = MutableLiveData<Film>()
    val liveFilmData = MutableLiveData<FilmEntity>()
    val error = MutableLiveData<String>()

    private var page: Int? = null

    fun searchFilmList(isReload: Boolean = false, query: String) {
        if (isReload) {
            page = 1
        }
        isLoading.value = true
        movieRepository.getFilmList(query, page ?: 1, this)
    }

    fun loadNowPlayingList(isLoadMore: Boolean = false) {
        if (isLoadMore) {
            page = 1
        }
        isLoading.value = true
        movieRepository.getNowPlayingList(page ?: 1, this)
    }

    fun loadFilmDetail(movieId: Long) {
        isLoading.value = true
        movieRepository.getFilmDetail(movieId, this)
    }

    override fun onSuccess(body: Film?) {
        page = body?.page?.plus(1)
        isLoading.value = false
        liveFilmListData.value = body
    }

    override fun onFilmDetailLoadSuccess(film: FilmEntity?) {
        isLoading.value = false
        liveFilmData.value = film
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