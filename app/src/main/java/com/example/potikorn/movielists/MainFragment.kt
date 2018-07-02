package com.example.potikorn.movielists

import android.animation.Animator
import android.app.Activity
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
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.potikorn.movielists.room.Film
import com.example.potikorn.movielists.room.FilmEntity
import com.example.potikorn.movielists.ui.FilmViewModel
import com.example.potikorn.movielists.ui.FilmViewModelFactory
import com.willowtreeapps.spruce.Spruce
import com.willowtreeapps.spruce.animation.DefaultAnimations
import com.willowtreeapps.spruce.sort.DefaultSort
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MainFragment : Fragment(), FilmAdapter.OnFilmClickListener, FilmAdapter.OnLoadMoreListener {

    @Inject
    lateinit var filmViewModelFactory: FilmViewModelFactory

    private var spruceAnimator: Animator? = null

    private val filmViewModel: FilmViewModel by lazy {
        ViewModelProviders.of(this, filmViewModelFactory).get(FilmViewModel::class.java)
    }
    private val filmAdapter: FilmAdapter? by lazy { FilmAdapter() }
    private var isLoadMore = false

    companion object {
        fun newInstance(): MainFragment {
            val mainFragment = MainFragment()
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
            setOnFilmClickListener(this@MainFragment)
            setOnLoadMoreListener(rv_movie_list, this@MainFragment)
        }
        srl.setOnRefreshListener {
            isLoadMore = true
            filmViewModel.loadNowPlayingList(true)
        }
        ivIconSearch.setOnClickListener {
            activity?.hideKeyboard()
            filmViewModel.searchFilmList(etSearch?.text.toString())
        }
    }

    private fun initViewModel() {
        filmViewModel.isLoading.observe(this, Observer { srl.isRefreshing = it ?: false })
        filmViewModel.error.observe(this, Observer { processError(it) })
        filmViewModel.liveFilmData.observe(this, Observer<Film> { filmModels ->
            filmAdapter?.setLoaded()
            filmModels?.let {
                when (isLoadMore) {
                    true -> {
                        filmAdapter?.clearItems()
                        rv_movie_list.scrollToPosition(0)
                        isLoadMore = false
                    }
                }
                filmAdapter?.setFilms(filmModels.movieDetails)
            } ?: Log.e("MAINFRAGMENT", "Data is null")
        })
        filmViewModel.loadNowPlayingList()
    }

    private fun processError(error: String?) =
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

    private fun Activity.hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

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
