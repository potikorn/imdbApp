package com.example.potikorn.movielists.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class Film {
    @SerializedName("page")
    var page: Int = 0
    @SerializedName("total_results")
    var totalResults: Int = 0
    @SerializedName("total_pages")
    var totalPages: Int = 0
    @SerializedName("results")
    var movieDetails: MutableList<FilmEntity> = ArrayList()
}

@Entity(tableName = RoomContract.TABLE_FILMS)
data class FilmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("title") var title: String,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("poster_path") var posterPath: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("release_date") var releaseDate: String
)