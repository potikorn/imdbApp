package com.example.potikorn.movielists.dao

import com.google.gson.annotations.SerializedName

data class FavoriteDao(
    @SerializedName("data") var data: MutableList<FavoriteDetailDao>? = null
) : BaseDao()

data class FavoriteDetailDao(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("created_at") var createdAt: String? = null
)