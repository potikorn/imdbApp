package com.example.potikorn.movielists.ui.setting.favorite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.ui.viewmodel.FavoriteViewModel
import com.example.potikorn.movielists.ui.viewmodel.FavoriteViewModelFactory
import javax.inject.Inject

class FavoriteFragment : BaseFragment() {

    @Inject
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by lazy {
        ViewModelProviders.of(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)
    }

    override fun layoutToInflate(): Int = R.layout.fragment_favorite

    override fun createLifecycleOwner(): FragmentLifecycleOwner = FragmentLifecycleOwner.create()

    override fun doInjection(appComponent: AppComponent) = appComponent.inject(this)

    override fun startView() {}

    override fun stopView() {}

    override fun destroyView() {}

    override fun setupInstance() {}

    override fun setupView() {
    }

    override fun initialize() {
        favoriteViewModel.liveFavoriteList.observe(this, Observer {

        })
        favoriteViewModel.getFavoriteList()
    }

    companion object {
        fun newInstance(bundle: Bundle? = null): FavoriteFragment {
            val favoriteFragment = FavoriteFragment()
            favoriteFragment.arguments = bundle
            return favoriteFragment
        }
    }
}