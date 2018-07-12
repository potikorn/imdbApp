package com.example.potikorn.movielists.ui.moviedetail.viewholder.genres

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.potikorn.movielists.room.FilmEntity
import com.example.potikorn.movielists.widget.ItemOffset
import kotlinx.android.synthetic.main.item_genres_view_group.view.*

class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val genreAdapter: GenreAdapter by lazy { GenreAdapter() }

    fun onBindData(film: FilmEntity?) {
        itemView.rvCategory.apply {
            addItemDecoration(ItemOffset(16))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter.apply { setGenreList(film?.genreDAO ?: mutableListOf()) }
        }
    }
}