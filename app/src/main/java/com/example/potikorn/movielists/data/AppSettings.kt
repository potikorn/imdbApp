package com.example.potikorn.movielists.data

import android.content.SharedPreferences

interface AppSettings {

    fun setLang(lang: String)
    fun getLang(): String
}

class SettingsData(private var pref: SharedPreferences) : AppSettings {

    val PREF_APP_LANG = "PREF_APP_LANG"

    override fun setLang(lang: String) = pref.edit().putString(PREF_APP_LANG, lang).apply()
    override fun getLang(): String = pref.getString(PREF_APP_LANG, "en-US")
}