package com.example.potikorn.movielists.ui.moviedetail.viewholder.recommend

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.potikorn.movielists.dao.Film
import com.example.potikorn.movielists.ui.adapter.PosterAdapter
import kotlinx.android.synthetic.main.item_recommend_movie.view.*

class RecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val posterAdapter: PosterAdapter by lazy { PosterAdapter() }

    fun onBindData(film: Film?) {
        itemView.rvPosterList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = posterAdapter.apply { setMovieList(film?.movieDetails ?: mutableListOf()) }
        }
    }
}