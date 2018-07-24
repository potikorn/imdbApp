package com.example.potikorn.movielists.ui.movielist

import android.animation.Animator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.dao.Film
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.extensions.showToast
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity.Companion.EXTRA_FILM_ID
import com.willowtreeapps.spruce.Spruce
import com.willowtreeapps.spruce.animation.DefaultAnimations
import com.willowtreeapps.spruce.sort.DefaultSort
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : Fragment(), MovieAdapter.OnFilmClickListener,
    MovieAdapter.OnLoadMoreListener {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    private var spruceAnimator: Animator? = null

    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
    }
    private val movieAdapter: MovieAdapter? by lazy { MovieAdapter() }
    private var isRefresh = false

    companion object {
        fun newInstance(): MovieListFragment {
            val mainFragment = MovieListFragment()
            val args = Bundle()
            mainFragment.arguments = args
            return mainFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImdbApplication.appComponent.inject(this)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance()
    }

    private fun initInstance() {
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
        movieViewModel.loadNowPlayingList()
    }

    private fun processError(error: String?) = activity?.showToast(error)

    override fun onFilmClick(film: FilmResult?) {
        startActivity(
            Intent(context, MovieDetailActivity::class.java)
                .putExtra(EXTRA_FILM_ID, film?.id)
        )
    }

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
}
