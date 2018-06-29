package com.example.potikorn.movielists

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.potikorn.movielists.room.FilmEntity
import kotlinx.android.synthetic.main.list_movie_item.view.*

class FilmAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var films: MutableList<FilmEntity> = mutableListOf()
    private var onFilmClickListener: OnFilmClickListener? = null

    fun setOnFilmClickListener(onFilmClickListener: OnFilmClickListener) {
        this.onFilmClickListener = onFilmClickListener
    }

    fun setFilms(films: MutableList<FilmEntity>) {
        this.films = films
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_movie_item, parent, false)
        return FilmHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FilmHolder).onBindData(films[position])
    }

    override fun getItemCount(): Int = films.size

    fun clearItems() {
        films.clear()
        notifyDataSetChanged()
    }

    inner class FilmHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        fun onBindData(film: FilmEntity) {
            itemView.tv_title.text = film.title
            itemView.tv_published_year.text =
                String.format("%.1f/10  :  %s", film.voteAverage, film.releaseDate)
            itemView.tv_overview.text = film.overview
            val imageUrl = BASE_IMAGE_URL + film.posterPath
            Glide.with(itemView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(itemView.iv_poster)
            itemView.setOnClickListener { onFilmClickListener?.onFilmClick(film) }
        }
    }

    interface OnFilmClickListener {
        fun onFilmClick(film: FilmEntity?)
    }
}
