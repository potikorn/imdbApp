package com.example.potikorn.movielists

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MainFragment : Fragment(), FilmAdapter.OnFilmClickListener {

    @Inject
    lateinit var filmViewModelFactory: FilmViewModelFactory

    private val filmViewModel: FilmViewModel by lazy {
        ViewModelProviders.of(this, filmViewModelFactory).get(FilmViewModel::class.java)
    }
    private val filmAdapter: FilmAdapter? by lazy { FilmAdapter() }

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
        rv_movie_list.layoutManager = LinearLayoutManager(context)
        rv_movie_list.adapter = filmAdapter?.apply {
            setOnFilmClickListener(this@MainFragment)
        }
        srl.setOnRefreshListener { filmViewModel.loadFilmList(etSearch?.text.toString()) }
        ivIconSearch.setOnClickListener {
            activity?.hideKeyboard()
            filmViewModel.loadFilmList(etSearch?.text.toString())
        }
    }

    private fun initViewModel() {
        filmViewModel.isLoading.observe(this, Observer { srl.isRefreshing = it ?: false })
        filmViewModel.error.observe(this, Observer { processError(it) })
        filmViewModel.liveFilmData.observe(this, Observer<Film> { filmModels ->
            filmModels?.let {
                rv_movie_list.scrollToPosition(0)
                filmAdapter?.setFilms(filmModels.movieDetails)
            } ?: Log.e("MAINFRAGMENT", "Data is null")

        })
        filmViewModel.loadFilmList("Pirate of the caribbean")
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
}
