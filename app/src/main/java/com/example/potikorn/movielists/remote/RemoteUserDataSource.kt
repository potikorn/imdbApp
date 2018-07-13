package com.example.potikorn.movielists.remote

import com.example.potikorn.movielists.httpmanager.UserApi
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(private val userService: UserApi) {

    fun requestGuestSession() = userService.getGuestSession()
}