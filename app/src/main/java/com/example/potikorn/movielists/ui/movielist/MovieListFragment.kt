package com.example.potikorn.movielists.ui.movielist

import android.animation.Animator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.dao.Film
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.extensions.showToast
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity.Companion.EXTRA_FILM_ID
import com.willowtreeapps.spruce.Spruce
import com.willowtreeapps.spruce.animation.DefaultAnimations
import com.willowtreeapps.spruce.sort.DefaultSort
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : BaseFragment(), MovieAdapter.OnFilmClickListener,
    MovieAdapter.OnLoadMoreListener {

    override fun layoutToInflate(): Int = R.layout.fragment_movie_list

    override fun createLifecycleOwner(): FragmentLifecycleOwner = FragmentLifecycleOwner.create()

    override fun doInjection(appComponent: AppComponent) = appComponent.inject(this)

    override fun startView() {}

    override fun stopView() {}

    override fun destroyView() {}

    override fun setupInstance() {
        initViewModel()
    }

    override fun setupView() {
        rv_movie_list.layoutManager = object : LinearLayoutManager(context) {
            override fun onLayoutChildren(
                recycler: RecyclerView.Recycler?,
                state: RecyclerView.State?
            ) {
                super.onLayoutChildren(recycler, state)
//                initSpruce()
            }
        }
        rv_movie_list.adapter = movieAdapter?.apply {
            setOnFilmClickListener(this@MovieListFragment)
            setOnLoadMoreListener(rv_movie_list, this@MovieListFragment)
        }
        srl.setOnRefreshListener {
            isRefresh = true
            movieViewModel.loadNowPlayingList(true)
        }
    }

    override fun initialize() {
        movieViewModel.loadNowPlayingList()
    }

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    private var spruceAnimator: Animator? = null

    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
    }
    private val movieAdapter: MovieAdapter? by lazy { MovieAdapter() }
    private var isRefresh = false

    override fun onFilmClick(film: FilmResult?) {
        startActivity(
            Intent(context, MovieDetailActivity::class.java)
                .putExtra(EXTRA_FILM_ID, film?.id)
        )
    }

    private fun initViewModel() {
        movieViewModel.isLoading.observe(this, Observer { srl.isRefreshing = it ?: false })
        movieViewModel.error.observe(this, Observer { processError(it) })
        movieViewModel.liveFilmListData.observe(this, Observer<Film> { filmModels ->
            movieAdapter?.setLoaded()
            filmModels?.let {
                when (isRefresh) {
                    true -> {
                        movieAdapter?.clearItems()
                        rv_movie_list.scrollToPosition(0)
                        isRefresh = false
                    }
                }
                movieAdapter?.setFilms(filmModels.movieDetails ?: arrayListOf())
            } ?: Log.e("MAINFRAGMENT", "Data is null")
        })
    }

    private fun processError(error: String?) = activity?.showToast(error)

    private fun initSpruce() {
        spruceAnimator = Spruce.SpruceBuilder(rv_movie_list)
            .sortWith(DefaultSort(100))
            .animateWith(DefaultAnimations.shrinkAnimator(rv_movie_list, 1000))
            .start()
    }

    override fun onLoadMore() {
        Log.e("Best", "ENTER LOAD MORE")
        movieViewModel.loadNowPlayingList()
    }

    companion object {
        fun newInstance(): MovieListFragment {
            val mainFragment = MovieListFragment()
            val args = Bundle()
            mainFragment.arguments = args
            return mainFragment
        }
    }
}
