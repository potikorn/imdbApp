package com.example.potikorn.movielists.remote

import com.example.potikorn.movielists.httpmanager.FirebaseApi
import com.google.gson.JsonObject
import javax.inject.Inject

class RemoteFirebaseDatasource @Inject constructor(private val remoteFirebaseService: FirebaseApi) {

    fun requestFavoriteMovie(filmObj: JsonObject?) = remoteFirebaseService.favoriteMovie(filmObj)
    fun requestFavoriteMovieList() = remoteFirebaseService.getFavoriteList()
}