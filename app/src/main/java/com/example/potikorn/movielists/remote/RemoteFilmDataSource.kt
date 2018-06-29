package com.example.potikorn.movielists.remote

import com.example.potikorn.movielists.httpmanager.MovieApi
import javax.inject.Inject

class RemoteFilmDataSource @Inject constructor(private val remoteFilmService: MovieApi) {

    fun requestFilmList(queryWord: String) = remoteFilmService.getMovieList(queryWord)

    fun requestNowPlayingList()= remoteFilmService.getMovieNowPlaying()
}