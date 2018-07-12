package com.example.potikorn.movielists.ui.moviedetail.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.potikorn.movielists.room.FilmEntity
import kotlinx.android.synthetic.main.item_movie_detail.view.*

class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBindData(film: FilmEntity?) {
        itemView.pbPopularity.progress = film?.popularity?.toInt() ?: 0
        itemView.tvUserScore.text = film?.popularity?.toInt().toString()
        itemView.tvOverview.text = film?.overview
    }
}