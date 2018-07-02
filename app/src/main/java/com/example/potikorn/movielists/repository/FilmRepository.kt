package com.example.potikorn.movielists.repository

import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.remote.RemoteFilmDataSource
import com.example.potikorn.movielists.room.Film
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmRepository @Inject constructor(private val remoteFilmDataSource: RemoteFilmDataSource) {

    fun getFilmList(query: String, callback: BaseSubscriber.SubscribeCallback<Film>) {
        remoteFilmDataSource.requestFilmList(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseSubscriber(callback))
    }

    fun getNowPlayingList(page: Int, callback: BaseSubscriber.SubscribeCallback<Film>) {
        remoteFilmDataSource.requestNowPlayingList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseSubscriber(callback))
    }

    fun funFunc(callback: BaseSubscriber.SubscribeCallback<Film>) {
        val searchList = remoteFilmDataSource.requestFilmList("X-men").toObservable()
        val nowPlaying = remoteFilmDataSource.requestNowPlayingList(page = 1).toObservable()
    }
}