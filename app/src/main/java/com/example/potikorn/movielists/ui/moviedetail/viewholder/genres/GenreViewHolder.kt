package com.example.potikorn.movielists.ui.moviedetail.viewholder.genres

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.potikorn.movielists.dao.GenreDao
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBindData(genre: GenreDao) {
        itemView.tvGenre.text = genre.name
    }
}