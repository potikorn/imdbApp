package com.example.potikorn.movielists.httpmanager;

import com.example.potikorn.movielists.dao.Film;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieApi {

    @GET("search/movie")
    Observable<Response<Film>> getMovieList(@Query("query") String keyword);

}
