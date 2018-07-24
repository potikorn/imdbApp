package com.example.potikorn.movielists.ui.moviedetail.viewholder.genres

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.dao.GenreDao
import com.example.potikorn.movielists.extensions.inflate

class GenreAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var genreList: MutableList<GenreDao> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        GenreViewHolder(parent.inflate(R.layout.item_genre))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? GenreViewHolder)?.onBindData(genreList[position])
    }

    override fun getItemCount(): Int = genreList.size

    fun setGenreList(list: MutableList<GenreDao>) {
        this.genreList = list
        notifyDataSetChanged()
    }
}