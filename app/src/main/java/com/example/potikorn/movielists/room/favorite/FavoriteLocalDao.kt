package com.example.potikorn.movielists.room.favorite

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.potikorn.movielists.room.RoomContract

@Dao
interface FavoriteLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg filmList: FavoriteEntity)

    @Query("SELECT * FROM ${RoomContract.TABLE_FAVORITE}")
    fun getAllFavoriteFilms(): LiveData<MutableList<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE movieId = :id")
    fun getFilmsById(id: Long): LiveData<FavoriteEntity>

    @Query("DELETE FROM favorite")
    fun deleteAll()
}