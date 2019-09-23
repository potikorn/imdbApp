package com.example.potikorn.movielists.ui.moviedetail

import com.example.potikorn.movielists.dao.FilmResult

interface OnActionClickListener {

    fun onFavoriteClick(filmModel: FilmResult)
}