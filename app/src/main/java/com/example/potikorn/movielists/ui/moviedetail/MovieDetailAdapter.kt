package com.example.potikorn.movielists.ui.moviedetail

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.extensions.inflate
import com.example.potikorn.movielists.ui.moviedetail.viewholder.DetailViewHolder
import com.example.potikorn.movielists.ui.moviedetail.viewholder.PosterViewHolder
import com.example.potikorn.movielists.ui.moviedetail.viewholder.genres.GenresViewHolder

class MovieDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var film: FilmResult? = null
    private val ITEM_TYPES = 3
    private val POSTER_TYPE = 0
    private val DETAIL_TYPE = 1
    private val GENRES_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            POSTER_TYPE -> PosterViewHolder(parent.inflate(R.layout.item_poster_movie_detail))
            DETAIL_TYPE -> DetailViewHolder(parent.inflate(R.layout.item_movie_detail))
            else -> GenresViewHolder(parent.inflate(R.layout.item_genres_view_group))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PosterViewHolder -> holder.onBindData(film)
            is DetailViewHolder -> holder.onBindData(film)
            else -> (holder as? GenresViewHolder)?.onBindData(film)
        }
    }

    override fun getItemCount(): Int = ITEM_TYPES

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> POSTER_TYPE
            1 -> DETAIL_TYPE
            else -> GENRES_TYPE
        }

    fun setItem(film: FilmResult?) {
        this.film = film
        notifyDataSetChanged()
    }
}