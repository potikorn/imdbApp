package com.example.potikorn.movielists.data

import android.content.SharedPreferences

interface User {
    fun setFirstTime(isFirstTime: Boolean)
    fun isFirstTime(): Boolean
    fun setLogin(isLogIn: Boolean)
    fun isLogin(): Boolean
    fun setUserId(userId: String?)
    fun getUserId(): String
    fun setSessionId(sessionId: String?)
    fun getSessionId(): String
    fun setSessionExpired(expiredDate: String?)
    fun getSessionExpired(): String
    fun clear()
}

class UserData(private var pref: SharedPreferences) : User {

    val PREF_IS_FIRST_TIME = "PREF_IS_FIRST_TIME"
    val PREF_IS_LOGIN = "PREF_IS_LOGIN"
    val PREF_USER_ID = "PREF_USER_ID"
    val PREF_SESSION_ID = "PREF_SESSION_ID"
    val PREF_SESSION_EXPIRED = "PREF_SESSION_EXPIRED"

    override fun setFirstTime(isFirstTime: Boolean) =
        pref.edit().putBoolean(PREF_IS_FIRST_TIME, isFirstTime).apply()

    override fun isFirstTime(): Boolean = pref.getBoolean(PREF_IS_FIRST_TIME, true)

    override fun setLogin(isLogIn: Boolean) =
        pref.edit().putBoolean(PREF_IS_LOGIN, isLogIn).apply()

    override fun isLogin(): Boolean = pref.getBoolean(PREF_IS_LOGIN, false)

    override fun setSessionId(sessionId: String?) =
        pref.edit().putString(PREF_SESSION_ID, sessionId).apply()

    override fun setUserId(userId: String?) = pref.edit().putString(PREF_USER_ID, userId).apply()

    override fun getUserId(): String = pref.getString(PREF_USER_ID, "")

    override fun getSessionId(): String = pref.getString(PREF_SESSION_ID, "")

    override fun setSessionExpired(expiredDate: String?) =
        pref.edit().putString(PREF_SESSION_EXPIRED, expiredDate).apply()

    override fun getSessionExpired(): String = pref.getString(PREF_SESSION_EXPIRED, "")

    override fun clear() = pref.edit().clear().apply()
}