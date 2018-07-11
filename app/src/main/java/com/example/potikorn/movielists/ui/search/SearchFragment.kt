package com.example.potikorn.movielists.ui.search

import android.animation.Animator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.extensions.showToast
import com.example.potikorn.movielists.room.Film
import com.example.potikorn.movielists.room.FilmEntity
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity
import com.example.potikorn.movielists.ui.movielist.MovieAdapter
import com.example.potikorn.movielists.ui.movielist.MovieViewModel
import com.example.potikorn.movielists.ui.movielist.MovieViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment(), MovieAdapter.OnFilmClickListener,
    MovieAdapter.OnLoadMoreListener {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    private var spruceAnimator: Animator? = null

    private val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
    }
    private val movieAdapter: MovieAdapter? by lazy { MovieAdapter() }

    companion object {
        fun newInstance(): SearchFragment {
            val mainFragment = SearchFragment()
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
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance()
    }

    private fun initInstance() {
        rv_movie_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter?.apply {
                setOnFilmClickListener(this@SearchFragment)
                setOnLoadMoreListener(rv_movie_list, this@SearchFragment)
            }
        }
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movieViewModel.searchFilmList(true, s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initViewModel() {
        movieViewModel.isLoading.observe(this, Observer { srl.isRefreshing = it ?: false })
        movieViewModel.error.observe(this, Observer { processError(it) })
        movieViewModel.liveFilmListData.observe(this, Observer<Film> { filmModels ->
            movieAdapter?.setLoaded()
            filmModels?.let {
                movieAdapter?.clearItems()
                movieAdapter?.setFilms(filmModels.movieDetails ?: arrayListOf())
            } ?: Log.e("MAINFRAGMENT", "Data is null")
        })
    }

    private fun processError(error: String?) = activity?.showToast(error)

    override fun onFilmClick(film: FilmEntity?) {
        startActivity(
            Intent(context, MovieDetailActivity::class.java)
                .putExtra(MovieDetailActivity.EXTRA_FILM_ID, film?.id)
        )
    }

    override fun onLoadMore() {
        Log.e("Best", "ENTER LOAD MORE")
        movieViewModel.searchFilmList(query = etSearch?.text.toString())
    }
}
