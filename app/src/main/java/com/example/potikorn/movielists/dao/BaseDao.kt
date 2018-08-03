package com.example.potikorn.movielists.dao

import com.google.gson.annotations.SerializedName

class BaseDao {
    @SerializedName("success")
    val success: Boolean? = false
    @SerializedName("message")
    val message: String? = null
}