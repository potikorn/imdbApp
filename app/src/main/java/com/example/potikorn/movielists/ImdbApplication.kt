package com.example.potikorn.movielists

import android.app.Application
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.di.AppModule
import com.example.potikorn.movielists.di.DaggerAppComponent
import com.example.potikorn.movielists.di.RemoteModule
import com.example.potikorn.movielists.di.RoomModule

class ImdbApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomModule(RoomModule())
            .remoteModule(RemoteModule()).build()
    }
}