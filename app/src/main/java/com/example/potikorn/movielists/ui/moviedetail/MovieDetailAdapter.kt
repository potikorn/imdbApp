package com.example.potikorn.movielists.ui.moviedetail

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.extensions.inflate
import com.example.potikorn.movielists.room.FilmEntity
import com.example.potikorn.movielists.ui.moviedetail.viewholder.DetailViewHolder
import com.example.potikorn.movielists.ui.moviedetail.viewholder.PosterViewHolder

class MovieDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var film: FilmEntity? = null
    private val ITEM_TYPES = 2
    private val POSTER_TYPE = 0
    private val DETAIL_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            POSTER_TYPE -> PosterViewHolder(parent.inflate(R.layout.item_poster_movie_detail))
            else -> DetailViewHolder(parent.inflate(R.layout.item_movie_detail))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PosterViewHolder -> holder.onBindData(film)
            else -> (holder as? DetailViewHolder)?.onBindData(film)
        }
    }

    override fun getItemCount(): Int = ITEM_TYPES

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> POSTER_TYPE
            else -> DETAIL_TYPE
        }

    fun setItem(film: FilmEntity?) {
        this.film = film
        notifyDataSetChanged()
    }
}