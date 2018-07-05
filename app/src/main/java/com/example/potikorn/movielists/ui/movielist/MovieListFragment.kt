package com.example.potikorn.movielists.ui.movielist

import android.animation.Animator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.room.Film
import com.example.potikorn.movielists.room.FilmEntity
import com.willowtreeapps.spruce.Spruce
import com.willowtreeapps.spruce.animation.DefaultAnimations
import com.willowtreeapps.spruce.sort.DefaultSort
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : Fragment(), FilmAdapter.OnFilmClickListener, FilmAdapter.OnLoadMoreListener {

    @Inject
    lateinit var filmViewModelFactory: FilmViewModelFactory

    private var spruceAnimator: Animator? = null

    private val filmViewModel: FilmViewModel by lazy {
        ViewModelProviders.of(this, filmViewModelFactory).get(FilmViewModel::class.java)
    }
    private val filmAdapter: FilmAdapter? by lazy { FilmAdapter() }
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
        rv_movie_list.adapter = filmAdapter?.apply {
            setOnFilmClickListener(this@MovieListFragment)
            setOnLoadMoreListener(rv_movie_list, this@MovieListFragment)
        }
        srl.setOnRefreshListener {
            isRefresh = true
            filmViewModel.loadNowPlayingList(true)
        }
    }

    private fun initViewModel() {
        filmViewModel.isLoading.observe(this, Observer { srl.isRefreshing = it ?: false })
        filmViewModel.error.observe(this, Observer { processError(it) })
        filmViewModel.liveFilmData.observe(this, Observer<Film> { filmModels ->
            filmAdapter?.setLoaded()
            filmModels?.let {
                when (isRefresh) {
                    true -> {
                        filmAdapter?.clearItems()
                        rv_movie_list.scrollToPosition(0)
                        isRefresh = false
                    }
                }
                filmAdapter?.setFilms(filmModels.movieDetails)
            } ?: Log.e("MAINFRAGMENT", "Data is null")
        })
        filmViewModel.loadNowPlayingList()
    }

    private fun processError(error: String?) =
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

    override fun onFilmClick(film: FilmEntity?) {
        Toast.makeText(context, "${film?.id} : ${film?.title}", Toast.LENGTH_SHORT).show()
    }

    private fun initSpruce() {
        spruceAnimator = Spruce.SpruceBuilder(rv_movie_list)
            .sortWith(DefaultSort(100))
            .animateWith(DefaultAnimations.shrinkAnimator(rv_movie_list, 1000))
            .start()
    }

    override fun onLoadMore() {
        Log.e("Best", "ENTER LOAD MORE")
        filmViewModel.loadNowPlayingList()
    }
}
