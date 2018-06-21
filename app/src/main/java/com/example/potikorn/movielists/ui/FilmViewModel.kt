package com.example.potikorn.movielists.ui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.repository.FilmRepositoryImpl
import com.example.potikorn.movielists.room.FilmEntity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FilmViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var filmRepositoryImpl: FilmRepositoryImpl

    private val compositeDisposable = CompositeDisposable()
    private var liveFilmData: LiveData<MutableList<FilmEntity>>? = null

    init {
        initializeDagger()
    }

    fun getFilmList(query: String): LiveData<MutableList<FilmEntity>> {
        if (filmRepositoryImpl.getTotalFilms()) {
            Log.e("BEST", "GET FROM API")
            liveFilmData = MutableLiveData<MutableList<FilmEntity>>()
            liveFilmData = filmRepositoryImpl.getFilmList(query)
        } else {
            Log.e("BEST", "GET FROM LOCAL DB")
            liveFilmData = filmRepositoryImpl.getLocalFilmList()
        }
        return liveFilmData ?: MutableLiveData<MutableList<FilmEntity>>()
    }

    fun reloadData() {
        Log.e("BEST", liveFilmData?.value.toString())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribeViewModel() {
        for (disposable in filmRepositoryImpl.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }

    private fun initializeDagger() = ImdbApplication.appComponent.inject(this)
}