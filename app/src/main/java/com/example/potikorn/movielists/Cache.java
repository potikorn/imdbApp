package com.example.potikorn.movielists;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.potikorn.movielists.dao.Film;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Cache {

    private static final String KEY_CACHE_DATA = "main_data";


    public static void saveCacheHome(Context context, Film list) {
        if (list != null) {
            String json = new Gson().toJson(list);
            SharedPreferences prefs = context.getSharedPreferences(KEY_CACHE_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("json", json);
            editor.apply();
        }
    }

    public static Film getCacheHome(Context context) {
        Film list = null;
        SharedPreferences prefs = context.getSharedPreferences(KEY_CACHE_DATA, Context.MODE_PRIVATE);
        String json = prefs.getString("json", null);
        if (json != null)
            list = new Gson().fromJson(json, new TypeToken<Film>() {
            }.getType());
        return list;
    }



}
