package com.example.potikorn.movielists.remote

import com.example.potikorn.movielists.httpmanager.MovieApi
import javax.inject.Inject

class RemoteFilmDataSource @Inject constructor(private val remoteFilmService: MovieApi) {

    fun requestFilmList(queryWord: String, page: Int) =
        remoteFilmService.getMovieList(queryWord, page)

    fun requestNowPlayingList(page: Int) = remoteFilmService.getMovieNowPlaying(page)

    fun requestFilmDetail(movieId: Long) = remoteFilmService.getFilmDetail(movieId)
}