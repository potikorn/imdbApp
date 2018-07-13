package com.example.potikorn.movielists.repository

import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.dao.GuestDao
import com.example.potikorn.movielists.remote.RemoteUserDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository @Inject constructor(private val remoteUserDataSource: RemoteUserDataSource) {

    fun getGuestSession(callback: BaseSubscriber.SubscribeCallback<GuestDao>) =
        remoteUserDataSource.requestGuestSession()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseSubscriber(callback))
}