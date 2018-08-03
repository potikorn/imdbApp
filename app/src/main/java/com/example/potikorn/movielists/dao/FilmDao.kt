package com.example.potikorn.movielists.dao

import com.google.gson.annotations.SerializedName

class Film {
    @SerializedName("page")
    var page: Int = 0
    @SerializedName("total_results")
    var totalResults: Int = 0
    @SerializedName("total_pages")
    var totalPages: Int = 0
    @SerializedName("results")
    var movieDetails: MutableList<FilmResult>? = null
}

data class FilmResult(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("backdrop_path") var backDropPath: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("genres") var genreDao: MutableList<GenreDao>? = null
)