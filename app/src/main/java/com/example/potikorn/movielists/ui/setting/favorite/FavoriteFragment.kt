package com.example.potikorn.movielists.ui.setting.favorite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity.Companion.EXTRA_FILM_ID
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
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter().apply { setOnFilmClickFunction { gotoMovieDetailActivity(it) } }
    }

    override fun layoutToInflate(): Int = R.layout.fragment_favorite

    override fun createLifecycleOwner(): FragmentLifecycleOwner = FragmentLifecycleOwner.create()

    override fun doInjection(appComponent: AppComponent) = appComponent.inject(this)

    override fun startView() {}

    override fun stopView() {}

    override fun destroyView() {}

    override fun setupInstance() {}

    override fun setupView() {
        rvFavoriteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    override fun initialize() {
        favoriteViewModel.getFavoriteList().observe(this, Observer { filmList ->
            filmList?.let {
                val filmResult = mutableListOf<FilmResult>()
                it.forEach { item ->
                    filmResult.add(
                        FilmResult(
                            id = item.movieId,
                            title = item.title,
                            posterPath = item.posterPath
                        )
                    )
                }
                movieAdapter.clearItems()
                movieAdapter.setFilms(filmResult)
            }
        })
    }

    private fun gotoMovieDetailActivity(film: FilmResult?) {
        startActivity(
            Intent(context, MovieDetailActivity::class.java).putExtra(EXTRA_FILM_ID, film?.id)
        )
    }

    companion object {
        fun newInstance(bundle: Bundle? = null): FavoriteFragment {
            val favoriteFragment = FavoriteFragment()
            favoriteFragment.arguments = bundle
            return favoriteFragment
        }
    }
}