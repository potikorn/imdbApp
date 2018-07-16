package com.example.potikorn.movielists.ui.movielist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.dao.Film
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.repository.MovieContract
import com.example.potikorn.movielists.repository.MovieRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel(),
    BaseSubscriber.SubscribeCallback<Film>,
    MovieContract.FilmDetailListener,
    MovieContract.ZipTwoObserverListener<FilmResult, Film> {

    val isLoading = MutableLiveData<Boolean>()
    val liveFilmListData = MutableLiveData<Film>()
    val liveFilmData = MutableLiveData<FilmResult>()
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

    fun loadMovieDetailAndRecommend(movieId: Long) {
        isLoading.value = true
        movieRepository.getMovieDetailAndRecommendation(movieId, this)
    }

    override fun onSuccess(body: Film?) {
        page = body?.page?.plus(1)
        isLoading.value = false
        liveFilmListData.value = body
    }

    override fun onFilmDetailLoadSuccess(film: FilmResult?) {
        isLoading.value = false
        liveFilmData.value = film
    }

    override fun onZippedSuccess(zippedData: Pair<FilmResult?, Film?>) {
        isLoading.value = false
        liveFilmData.value = zippedData.first
        liveFilmListData.value = zippedData.second
    }

    override fun onUnSuccess(message: String?) {
        isLoading.value = false
        error.value = message
    }

    override fun onObservableError(message: String?) {
        isLoading.value = false
        error.value = message
    }

    override fun onUnAuthorized() {}
}