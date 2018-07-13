package com.example.potikorn.movielists.ui.movielist

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.potikorn.movielists.BASE_IMAGE_PATH
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.dao.FilmResult
import kotlinx.android.synthetic.main.list_movie_item.view.*

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_ITEM = 0
    val VIEW_PROGRESS = 1

    private var films: MutableList<FilmResult?> = mutableListOf()
    private var onFilmClickListener: OnFilmClickListener? = null
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var loading: Boolean = false

    fun setOnFilmClickListener(onFilmClickListener: OnFilmClickListener) {
        this.onFilmClickListener = onFilmClickListener
    }

    fun setFilms(films: MutableList<FilmResult>) {
        this.films.addAll(this.films.size, films)
        notifyItemRangeChanged(this.films.size.plus(1), this.films.size.plus(films.size))
    }

    fun setLoadMore(film: FilmResult?) {
        this.films.add(this.films.size.plus(1), film)
        notifyItemChanged(this.films.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_ITEM -> return FilmHolder(
                inflater.inflate(
                    R.layout.list_movie_item,
                    parent,
                    false
                )
            )
            else -> return LoadMoreViewHolder(
                inflater.inflate(
                    R.layout.item_progress,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmHolder -> holder.onBindData(films[position])
            else -> (holder as LoadMoreViewHolder).onBindData()
        }
    }

    override fun getItemCount(): Int = films.size

    override fun getItemViewType(position: Int): Int {
        return if (films[position] != null) VIEW_ITEM else VIEW_PROGRESS
    }

    fun clearItems() {
        films.clear()
        notifyDataSetChanged()
    }

    inner class FilmHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBindData(film: FilmResult?) {
            itemView.tv_title.text = film?.title
            itemView.tv_published_year.text =
                String.format("%.1f/10  :  %s", film?.voteAverage, film?.releaseDate)
            itemView.tv_overview.text = film?.overview
            val imageUrl = BASE_IMAGE_PATH + film?.posterPath
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

    inner class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBindData() {
        }
    }

    interface OnFilmClickListener {
        fun onFilmClick(film: FilmResult?)
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    fun setOnLoadMoreListener(recyclerView: RecyclerView, onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = (recyclerView?.layoutManager as LinearLayoutManager).itemCount
                val lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loading && totalItemCount <= (lastVisibleItem + 3)) {
                    onLoadMoreListener.onLoadMore()
                    loading = true
                }
            }
        })
    }

    fun setLoaded() {
        loading = false
    }
}
