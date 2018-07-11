package com.example.potikorn.movielists.di

import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity
import com.example.potikorn.movielists.ui.movielist.MovieListFragment
import com.example.potikorn.movielists.ui.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (RoomModule::class), (RemoteModule::class)])
@Singleton
interface AppComponent {
    fun inject(movieListFragment: MovieListFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(movieDetailActivity: MovieDetailActivity)
}
