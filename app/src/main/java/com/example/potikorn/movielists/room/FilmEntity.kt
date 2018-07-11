package com.example.potikorn.movielists.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class Film {
    @SerializedName("page")
    var page: Int = 0
    @SerializedName("total_results")
    var totalResults: Int = 0
    @SerializedName("total_pages")
    var totalPages: Int = 0
    @SerializedName("results")
    var movieDetails: MutableList<FilmEntity>? = null
}

@Entity(tableName = RoomContract.TABLE_FILMS)
data class FilmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("title") var title: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null
)