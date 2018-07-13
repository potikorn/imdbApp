package com.example.potikorn.movielists.dao

import com.google.gson.annotations.SerializedName

data class GenreDao(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null
)