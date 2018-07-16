package com.example.potikorn.movielists.ui.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.potikorn.movielists.BASE_IMAGE_PATH
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.dao.FilmResult
import com.example.potikorn.movielists.extensions.inflate
import com.example.potikorn.movielists.extensions.loadImageView
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity
import com.example.potikorn.movielists.ui.moviedetail.MovieDetailActivity.Companion.EXTRA_FILM_ID
import kotlinx.android.synthetic.main.item_poster.view.*

class PosterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var films: MutableList<FilmResult?> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PosterViewHolder(parent.inflate(R.layout.item_poster))

    override fun getItemCount(): Int = films.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? PosterViewHolder)?.onBindData(films[position])
    }

    fun setMovieList(movieList: MutableList<FilmResult>) {
        films.addAll(movieList)
        notifyDataSetChanged()
    }

    inner class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBindData(film: FilmResult?) {
            itemView.ivPoster.loadImageView("$BASE_IMAGE_PATH${film?.posterPath}")
            itemView.tvTitle.text = film?.title
            itemView.setOnClickListener {
                itemView.context.startActivity(
                    Intent(itemView.context, MovieDetailActivity::class.java)
                        .putExtra(EXTRA_FILM_ID, film?.id)
                )
            }
        }
    }
}