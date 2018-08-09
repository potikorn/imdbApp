package com.example.potikorn.movielists.ui.moviedetail

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.dao.Film
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.extensions.inflate
import com.example.potikorn.movielists.ui.moviedetail.viewholder.DetailViewHolder
import com.example.potikorn.movielists.ui.moviedetail.viewholder.PosterViewHolder
import com.example.potikorn.movielists.ui.moviedetail.viewholder.genres.GenresViewHolder
import com.example.potikorn.movielists.ui.moviedetail.viewholder.recommend.RecommendViewHolder

class MovieDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var film: FilmResult? = null
    var recommendFilmList: Film? = null

    private var onActionClick: OnActionClickListener? = null
    private val ITEM_TYPES = 4
    private val POSTER_TYPE = 0
    private val DETAIL_TYPE = 1
    private val GENRES_TYPE = 2
    private val RECOMMEND_TYPE = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            POSTER_TYPE -> PosterViewHolder(parent.inflate(R.layout.item_poster_movie_detail))
            DETAIL_TYPE -> DetailViewHolder(parent.inflate(R.layout.item_movie_detail))
            GENRES_TYPE -> GenresViewHolder(parent.inflate(R.layout.item_genres_view_group))
            else -> RecommendViewHolder(parent.inflate(R.layout.item_recommend_movie))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PosterViewHolder -> holder.onBindData(film, onActionClick)
            is DetailViewHolder -> holder.onBindData(film)
            is GenresViewHolder -> holder.onBindData(film)
            else -> (holder as? RecommendViewHolder)?.onBindData(recommendFilmList)
        }
    }

    override fun getItemCount(): Int = ITEM_TYPES

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> POSTER_TYPE
            1 -> DETAIL_TYPE
            2 -> GENRES_TYPE
            else -> RECOMMEND_TYPE
        }

    fun setItem(film: FilmResult?) {
        this.film = film
        notifyDataSetChanged()
    }

    fun setRecommendMovie(recommendMovie: Film?) {
        recommendFilmList = recommendMovie
        notifyItemChanged(3)
    }

    fun setOnActionClickListener(callback: OnActionClickListener) {
        onActionClick = callback
    }

    fun onPosterChanged() {

    }
}