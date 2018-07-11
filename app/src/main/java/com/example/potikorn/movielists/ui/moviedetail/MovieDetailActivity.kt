package com.example.potikorn.movielists.ui.moviedetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.extensions.showToast
import com.example.potikorn.movielists.room.FilmEntity
import com.example.potikorn.movielists.ui.movielist.MovieViewModel
import com.example.potikorn.movielists.ui.movielist.MovieViewModelFactory
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
    }
    private var filmId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImdbApplication.appComponent.inject(this)
        setContentView(R.layout.activity_movie_detail)
        getFilmId()
        initViewModel()
    }

    private fun initViewModel() {
        movieViewModel.isLoading.observe(this, Observer {})
        movieViewModel.error.observe(this, Observer { showToast(it) })
        movieViewModel.liveFilmData.observe(this, Observer {
            setMovieDetail(it)
        })
        movieViewModel.loadFilmDetail(filmId ?: 0)
    }

    private fun setMovieDetail(film: FilmEntity?) {

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