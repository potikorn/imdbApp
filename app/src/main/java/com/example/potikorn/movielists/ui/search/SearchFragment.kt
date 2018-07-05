package com.example.potikorn.movielists.ui.search

import android.animation.Animator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.room.Film
import com.example.potikorn.movielists.room.FilmEntity
import com.example.potikorn.movielists.ui.movielist.FilmAdapter
import com.example.potikorn.movielists.ui.movielist.FilmViewModel
import com.example.potikorn.movielists.ui.movielist.FilmViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment(), FilmAdapter.OnFilmClickListener, FilmAdapter.OnLoadMoreListener {

    @Inject
    lateinit var filmViewModelFactory: FilmViewModelFactory

    private var spruceAnimator: Animator? = null

    private val filmViewModel: FilmViewModel by lazy {
        ViewModelProviders.of(this, filmViewModelFactory).get(FilmViewModel::class.java)
    }
    private val filmAdapter: FilmAdapter? by lazy { FilmAdapter() }

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
            adapter = filmAdapter?.apply {
                setOnFilmClickListener(this@SearchFragment)
                setOnLoadMoreListener(rv_movie_list, this@SearchFragment)
            }
        }
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filmViewModel.searchFilmList(true, s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initViewModel() {
        filmViewModel.isLoading.observe(this, Observer { srl.isRefreshing = it ?: false })
        filmViewModel.error.observe(this, Observer { processError(it) })
        filmViewModel.liveFilmData.observe(this, Observer<Film> { filmModels ->
            filmAdapter?.setLoaded()
            filmModels?.let {
                filmAdapter?.clearItems()
                filmAdapter?.setFilms(filmModels.movieDetails)
            } ?: Log.e("MAINFRAGMENT", "Data is null")
        })
    }

    private fun processError(error: String?) =
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

    override fun onFilmClick(film: FilmEntity?) {
        Toast.makeText(context, "${film?.id} : ${film?.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onLoadMore() {
        Log.e("Best", "ENTER LOAD MORE")
        filmViewModel.searchFilmList(query = etSearch?.text.toString())
    }
}
