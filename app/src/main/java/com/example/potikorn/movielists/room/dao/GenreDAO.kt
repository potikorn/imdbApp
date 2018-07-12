package com.example.potikorn.movielists.room.dao

import com.google.gson.annotations.SerializedName

data class GenreDAO(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null
)