package com.example.potikorn.movielists.repository

import com.example.potikorn.movielists.dao.FilmResult
import retrofit2.Response

class MovieContract {

    interface FilmDetailListener {
        fun onFilmDetailLoadSuccess(film: FilmResult?)
        fun onUnSuccess(message: String?)
        fun onObservableError(message: String?)
    }

    interface ZipTwoObserverListener<T1, T2> {
        fun onZippedSuccess(zippedData: Pair<T1?, T2?>)
        fun onUnSuccess(message: String?)
        fun onObservableError(message: String?)
    }

    fun <T1, T2> validateZippedResponse(
        it: Pair<Response<T1>, Response<T2>>,
        callback: MovieContract.ZipTwoObserverListener<T1, T2>
    ) {
        when {
            it.first.isSuccessful && it.second.isSuccessful -> {
                callback.onZippedSuccess(it.first.body() to it.second.body())
            }
//            it.first.isSuccessful -> {
//                callback.onZippedSuccess(it.first.body() to )
//            }
//            !it.first.isSuccessful && it.second.isSuccessful -> {
//                callback.onGetExpertDetailAdsSuccess(Expert() to it.second.body())
//            }
//            it.first.code() == HttpURLConnection.HTTP_UNAUTHORIZED ||
//                it.second.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> callback.onUnAuthorized()
            else -> {
                val message = if (it.first.message().isNotEmpty()) {
                    it.first.message()
                } else {
                    it.second.message()
                }
                callback.onObservableError(message)
            }
        }
    }

    interface FavoriteListener {
        fun onUnSuccess(message: String?)
        fun onObservableError(message: String?)
        fun onUnAuthorized()
    }
}