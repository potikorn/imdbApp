package com.example.potikorn.movielists.httpmanager

import com.example.potikorn.movielists.dao.GuestDao
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("authentication/guest_session/new")
    fun getGuestSession(): Single<Response<GuestDao>>
}