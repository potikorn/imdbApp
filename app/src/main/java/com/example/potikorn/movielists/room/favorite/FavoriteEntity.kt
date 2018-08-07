package com.example.potikorn.movielists.room.favorite

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.example.potikorn.movielists.room.RoomContract

@Entity(tableName = RoomContract.TABLE_FAVORITE)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var movieId: Long? = null,
    var title: String? = null,
    var posterPath: String? = null
) {
    @Ignore
    constructor() : this(id = null)
}