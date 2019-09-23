package com.example.potikorn.movielists.httpmanager

import com.example.potikorn.movielists.dao.BaseDao
import com.example.potikorn.movielists.dao.FavoriteDao
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface FirebaseApi {

    @PUT("favorite-movie")
    fun favoriteMovie(@Body filmObj: JsonObject?): Single<Response<BaseDao>>

    @GET("favorite-movie")
    fun getFavoriteList(): Single<Response<FavoriteDao>>
}