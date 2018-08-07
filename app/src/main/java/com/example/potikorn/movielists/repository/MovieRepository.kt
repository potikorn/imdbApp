package com.example.potikorn.movielists.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.dao.BaseDao
import com.example.potikorn.movielists.dao.FavoriteDao
import com.example.potikorn.movielists.dao.Film
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.remote.RemoteFilmDataSource
import com.example.potikorn.movielists.remote.RemoteFirebaseDatasource
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteFilmDataSource: RemoteFilmDataSource,
    private val remoteFirebaseDatasource: RemoteFirebaseDatasource
) {

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

                override fun onUnAuthorized() {}
            }))
    }

    @SuppressLint("CheckResult")
    fun getMovieDetailAndRecommendation(
        movieId: Long,
        callback: MovieContract.ZipTwoObserverListener<FilmResult, Film>
    ) {
        val movieDetail =
            remoteFilmDataSource.requestFilmDetail(movieId).toObservable()
        val recommendation =
            remoteFilmDataSource.requestMovieRecommendationList(movieId).toObservable()
        Observable.zip(
            movieDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()),
            recommendation
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()),
            BiFunction<Response<FilmResult>, Response<Film>, Pair<Response<FilmResult>,
                Response<Film>>> { t1, t2 -> return@BiFunction t1 to t2 })
            .subscribe(
                { MovieContract().validateZippedResponse(it, callback) },
                { callback.onObservableError(it.message) })
    }

    fun rateMovie(filmObj: JsonObject?, callback: MovieContract.FavoriteListener) {
        remoteFirebaseDatasource.requestFavoriteMovie(filmObj)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseSubscriber(object : BaseSubscriber.SubscribeCallback<BaseDao> {
                override fun onSuccess(body: BaseDao?) {}
                override fun onUnSuccess(message: String?) = callback.onUnSuccess(message)
                override fun onObservableError(message: String?) =
                    callback.onObservableError(message)

                override fun onUnAuthorized() = callback.onUnAuthorized()
            }))
    }

    fun getFavoriteList(): FavoriteDao? {
        remoteFirebaseDatasource.requestFavoriteMovieList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(MovieRepository::class.java.simpleName, "$it")
            }, {
                it.printStackTrace()
                Log.e(MovieRepository::class.java.simpleName, "${it.message}")
            })
        return FavoriteDao()
    }
}