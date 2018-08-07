package com.example.potikorn.movielists.ui.setting.favorite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.ui.movielist.MovieAdapter
import com.example.potikorn.movielists.ui.viewmodel.FavoriteViewModel
import com.example.potikorn.movielists.ui.viewmodel.FavoriteViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : BaseFragment() {

    @Inject
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by lazy {
        ViewModelProviders.of(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)
    }
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }

    override fun layoutToInflate(): Int = R.layout.fragment_favorite

    override fun createLifecycleOwner(): FragmentLifecycleOwner = FragmentLifecycleOwner.create()

    override fun doInjection(appComponent: AppComponent) = appComponent.inject(this)

    override fun startView() {}

    override fun stopView() {}

    override fun destroyView() {}

    override fun setupInstance() {
        favoriteViewModel.liveFavoriteList.observe(this, Observer { filmList ->
            filmList?.let {
                val filmResult = mutableListOf<FilmResult>()
                it.data?.forEach { item ->
                    filmResult.add(
                        FilmResult(
                            id = item.id,
                            title = item.title,
                            posterPath = item.posterPath
                        )
                    )
                }
                movieAdapter.setFilms(filmResult)
            }
        })
    }

    override fun setupView() {
        rvFavoriteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    override fun initialize() {
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