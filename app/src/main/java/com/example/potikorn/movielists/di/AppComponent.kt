package com.example.potikorn.movielists.di

import com.example.potikorn.movielists.MainFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (RoomModule::class), (RemoteModule::class)])
@Singleton
interface AppComponent {
    fun inject(mainFragment: MainFragment)
}
