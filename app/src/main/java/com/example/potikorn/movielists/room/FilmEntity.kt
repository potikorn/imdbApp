package com.example.potikorn.movielists.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = RoomContract.TABLE_FILMS)
data class FilmEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var title: String? = null,
    var voteAverage: Double? = null,
    var posterPath: String? = null,
    var overview: String? = null,
    var releaseDate: String? = null,
    var backDropPath: String? = null,
    var popularity: Double? = null
)