package com.example.potikorn.movielists.repository

import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.dao.Film
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.remote.RemoteFilmDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val remoteFilmDataSource: RemoteFilmDataSource) {

    fun getFilmList(query: String, page: Int, callback: BaseSubscriber.SubscribeCallback<Film>) {
        remoteFilmDataSource.requestFilmList(query, page)
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

    fun getFilmDetail(movieId: Long, callback: MovieContract.FilmDetailListener) {
        remoteFilmDataSource.requestFilmDetail(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseSubscriber(object : BaseSubscriber.SubscribeCallback<FilmResult> {
                override fun onSuccess(body: FilmResult?) = callback.onFilmDetailLoadSuccess(body)
                override fun onUnSuccess(message: String?) = callback.onUnSuccess(message)
                override fun onObservableError(message: String?) =
                    callback.onObservableError(message)
            }))
    }

    fun funFunc(callback: BaseSubscriber.SubscribeCallback<Film>) {
//        val searchList = remoteFilmDataSource.requestFilmList("X-men", 1).toObservable()
//        val nowPlaying = remoteFilmDataSource.requestNowPlayingList(page = 1).toObservable()
    }
}