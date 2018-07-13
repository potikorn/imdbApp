package com.example.potikorn.movielists.dao

import com.google.gson.annotations.SerializedName

data class GuestDao(
    @SerializedName("guest_session_id") val guestSessionId: String? = null,
    @SerializedName("expires_at") val expireAt: String

)