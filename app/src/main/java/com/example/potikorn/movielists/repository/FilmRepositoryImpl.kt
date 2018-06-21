package com.example.potikorn.movielists.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.potikorn.movielists.remote.RemoteFilmDataSource
import com.example.potikorn.movielists.room.FilmDatabase
import com.example.potikorn.movielists.room.FilmEntity
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmRepositoryImpl @Inject constructor(
    private val roomFilmDatabase: FilmDatabase,
    private val remoteFilmDataSource: RemoteFilmDataSource
) : FilmRepository {

    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    override fun getTotalFilms(): Boolean {
        var isEmpty = true
        val disposable = roomFilmDatabase.filmDao().getTotalFilms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isEmpty = it == 0
            }, { t -> t.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return isEmpty
    }

    override fun addFilmList() {
    }

    override fun getFilmList(query: String): LiveData<MutableList<FilmEntity>> {
        val mutableLiveData = MutableLiveData<MutableList<FilmEntity>>()
        val disposable = remoteFilmDataSource.requestFilmList(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ filmList ->
                if (filmList.isSuccessful) {
                    mutableLiveData.value = filmList.body()?.movieDetails?.toMutableList()
                    // save to room db
//                    saveToRoomDB(filmList.body()?.movieDetails ?: mutableListOf())
                }
            }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    override fun getLocalFilmList(): LiveData<MutableList<FilmEntity>> {
        val mutableLiveData = MutableLiveData<MutableList<FilmEntity>>()
        val disposable = roomFilmDatabase.filmDao().getAllFilms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ filmList ->
                mutableLiveData.value = filmList
            }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun saveToRoomDB(filmEntities: MutableList<FilmEntity>) {
        roomFilmDatabase.filmDao().insertAll(filmEntities)
        Completable.fromAction { roomFilmDatabase.filmDao().insertAll(filmEntities) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(@NonNull d: Disposable) {
                    allCompositeDisposable.add(d)
                }

                override fun onComplete() {
                    Log.i(
                        FilmRepositoryImpl::class.java.simpleName,
                        "DataSource has been Populated"
                    )
                }

                override fun onError(@NonNull e: Throwable) {
                    e.printStackTrace()
                    Log.e(
                        FilmRepositoryImpl::class.java.simpleName,
                        "DataSource hasn't been Populated"
                    )
                }
            })
    }
}