package com.example.potikorn.movielists.httpmanager

import com.example.potikorn.movielists.dao.Film
import com.example.potikorn.movielists.dao.FilmResult
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie")
    fun getMovieList(
        @Query("query") keyword: String,
        @Query("page") page: Int? = 1
    ): Single<Response<Film>>

    @GET("movie/now_playing")
    fun getMovieNowPlaying(@Query("page") page: Int? = 1): Single<Response<Film>>

    @GET("movie/{movie_id}")
    fun getFilmDetail(@Path("movie_id") movieId: Long): Single<Response<FilmResult>>

    @GET("movie/{movie_id}/recommendations")
    fun getMovieRecommendation(@Path("movie_id") movieId: Long): Single<Response<Film>>
}
