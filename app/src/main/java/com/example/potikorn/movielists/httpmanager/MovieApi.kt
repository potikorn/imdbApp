package com.example.potikorn.movielists.httpmanager

import com.example.potikorn.movielists.room.Film

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie")
    fun getMovieList(@Query("query") keyword: String): Observable<Response<Film>>
}
