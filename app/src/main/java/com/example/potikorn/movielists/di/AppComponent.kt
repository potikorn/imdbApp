package com.example.potikorn.movielists.di

import com.example.potikorn.movielists.ui.login.LoginActivity
import com.example.potikorn.movielists.ui.login.fragment.LoginFragment
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity
import com.example.potikorn.movielists.ui.movielist.MovieListFragment
import com.example.potikorn.movielists.ui.register.RegisterActivity
import com.example.potikorn.movielists.ui.search.SearchFragment
import com.example.potikorn.movielists.ui.setting.MainSettingFragment
import com.example.potikorn.movielists.ui.setting.SettingFragment
import com.example.potikorn.movielists.ui.setting.favorite.FavoriteFragment
import com.example.potikorn.movielists.ui.splashscreen.SplashScreenActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (RoomModule::class), (RemoteModule::class)])
@Singleton
interface AppComponent {
    fun inject(splashScreenActivity: SplashScreenActivity)
    fun inject(movieListFragment: MovieListFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(movieDetailActivity: MovieDetailActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(settingFragment: SettingFragment)
    fun inject(registerActivity: RegisterActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(mainSettingFragment: MainSettingFragment)
    fun inject(favoriteFragment: FavoriteFragment)
}
