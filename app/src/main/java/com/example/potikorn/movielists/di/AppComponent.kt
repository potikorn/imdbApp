package com.example.potikorn.movielists.di

import com.example.potikorn.movielists.ui.FilmViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (RoomModule::class), (RemoteModule::class)])
@Singleton
interface AppComponent {
//    fun inject(currencyViewModel: CurrencyViewModel)
    fun inject(filmViewModel: FilmViewModel)
}
