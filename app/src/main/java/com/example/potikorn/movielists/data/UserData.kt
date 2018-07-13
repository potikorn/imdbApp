package com.example.potikorn.movielists.data

import android.content.SharedPreferences

interface User {
    fun setLogin(isLogIn: Boolean)
    fun isLogin(): Boolean
    fun setSessionId(sessionId: String?)
    fun getSessionId(): String
    fun setSessionExpired(expiredDate: String?)
    fun getSessionExpired(): String
}

class UserData(private var pref: SharedPreferences) : User {

    val PREF_IS_LOGIN = "PREF_IS_LOGIN"
    val PREF_SESSION_ID = "PREF_SESSION_ID"
    val PREF_SESSION_EXPIRED = "PREF_SESSION_EXPIRED"

    override fun setLogin(isLogIn: Boolean) =
        pref.edit().putBoolean(PREF_IS_LOGIN, isLogIn).apply()

    override fun isLogin(): Boolean = pref.getBoolean(PREF_IS_LOGIN, false)

    override fun setSessionId(sessionId: String?) =
        pref.edit().putString(PREF_SESSION_ID, sessionId).apply()

    override fun getSessionId(): String = pref.getString(PREF_SESSION_ID, "")

    override fun setSessionExpired(expiredDate: String?) =
        pref.edit().putString(PREF_SESSION_EXPIRED, expiredDate).apply()

    override fun getSessionExpired(): String = pref.getString(PREF_SESSION_EXPIRED, "")
}