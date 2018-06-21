package com.example.potikorn.movielists

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.potikorn.movielists.room.FilmEntity
import com.example.potikorn.movielists.ui.FilmViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MainFragment : Fragment() {

    private val filmViewModel: FilmViewModel by lazy {
        ViewModelProviders.of(this).get(FilmViewModel::class.java)
    }
    private var filmAdapter: FilmAdapter? = null

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
        filmAdapter = FilmAdapter(FilmAdapter.OnFilmClickListener {}, context)
        rv_movie_list.layoutManager = LinearLayoutManager(context)
        rv_movie_list.adapter = filmAdapter
        srl.setOnRefreshListener { filmViewModel.getFilmList(etSearch?.text.toString()) }
        ivIconSearch.setOnClickListener {
            filmAdapter?.clearItems()
            filmViewModel.getFilmList(etSearch?.text.toString())
        }
    }

    private fun initViewModel() {
        lifecycle.addObserver(filmViewModel)
        filmViewModel.getFilmList("Pirate of the caribbean")
            .observe(this, Observer<MutableList<FilmEntity>> { filmModels ->
                srl?.isRefreshing = false
                if (filmModels != null) {
                    filmAdapter?.setFilms(filmModels)
                } else {
                    Log.e("MAINFRAGMENT", "Data is null")
                }
            })
    }
}
