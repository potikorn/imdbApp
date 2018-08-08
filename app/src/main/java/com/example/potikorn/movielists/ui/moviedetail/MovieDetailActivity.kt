package com.example.potikorn.movielists.ui.moviedetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.extensions.showToast
import com.example.potikorn.movielists.ui.movielist.MovieViewModel
import com.example.potikorn.movielists.ui.movielist.MovieViewModelFactory
import com.example.potikorn.movielists.ui.viewmodel.FavoriteViewModel
import com.example.potikorn.movielists.ui.viewmodel.FavoriteViewModelFactory
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity(),
    OnActionClickListener {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
    }
    @Inject
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by lazy {
        ViewModelProviders.of(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)
    }

    private val movieDetailAdapter: MovieDetailAdapter by lazy { MovieDetailAdapter() }

    private var filmId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImdbApplication.appComponent.inject(this)
        setContentView(R.layout.activity_movie_detail)
        setupView()
        getFilmId()
        initViewModel()
    }

    override fun onFavoriteClick(filmModel: FilmResult) {
        movieViewModel.favoriteMovie(filmModel)
    }

    private fun setupView() {
        rvMainMovieDetail.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailActivity)
            adapter = movieDetailAdapter.apply {
                setOnActionClickListener(this@MovieDetailActivity)
            }
        }
    }

    private fun initViewModel() {
        movieViewModel.isLoading.observe(this, Observer {})
        movieViewModel.error.observe(this, Observer { showToast(it) })
        movieViewModel.liveFilmData.observe(this, Observer {
            movieDetailAdapter.setItem(it)
        })
        movieViewModel.liveFilmListData.observe(this, Observer {
            movieDetailAdapter.setRecommendMovie(it)
        })
        movieViewModel.loadMovieDetailAndRecommend(filmId ?: 0)
        favoriteViewModel.getFavoriteById(filmId ?: 0).observe(this, Observer { FilmEntity ->
            FilmEntity?.let {
                Log.e(MovieDetailActivity::class.java.simpleName, "data is existed")
            } ?: Log.e(MovieDetailActivity::class.java.simpleName, "data is not exited")
        })
    }

    private fun getFilmId() {
        intent?.let {
            filmId = it.getLongExtra(EXTRA_FILM_ID, 0)
        }
    }

    companion object {
        const val EXTRA_FILM_ID = "FILM_ID"
    }
}